package com.example.backend.chat.repository;

import com.sparta.daengtionary.aop.redis.RedisSubscriber;
import com.sparta.daengtionary.category.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class ChatRoomRedisRepository {
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private final StringRedisTemplate stringRedisTemplate;
    private ValueOperations<String, String> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = stringRedisTemplate.opsForValue();
    }

    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    public void createChatRoom(ChatRoom chatRoom) {
        opsHashChatRoom.put(CHAT_ROOMS, String.valueOf(chatRoom.getRoomNo()), chatRoom);
    }

    public void enterChatRoom(String roomNo) {
        if (topics.get(roomNo) == null) {
            ChannelTopic topic = new ChannelTopic(roomNo);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.set(roomNo, topic.toString());
            redisTemplate.expire(roomNo, 48, TimeUnit.HOURS);
        } else {
            String topicToString = topics.get(roomNo);
            ChannelTopic topic = new ChannelTopic(topicToString);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
        }
    }

    public ChannelTopic getTopic(String roomNo) {
        String topicToString = topics.get(roomNo);
        return new ChannelTopic(topicToString);
    }
}