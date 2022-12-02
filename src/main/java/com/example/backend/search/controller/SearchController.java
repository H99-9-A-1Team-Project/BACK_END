package com.example.backend.search.controller;

import com.example.backend.global.security.auth.UserDetailsImpl;
import com.example.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Api(tags = {"검색(상품 검색) API 정보를 제공하는 Controller"})    /* Class를 Swagger의 Resource로 표시, tags -> Swagger UI 페이지에 노출 될 태그 설명 */
@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

//    /* 상품 기본 검색 - 최신순 정렬('item - title / content'를 바탕으로) */
////    @ApiOperation(value = "keyword가 title 혹은 content에 포함되어있는 item을 찾는 검색 메소드 - 최신순 정렬")  /* 특정 경로의 Operation Http Method 설명 */



@GetMapping("items/search")
    public ResponseEntity<?> searchItem(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestParam("keyword") String keyword) {
        return ResponseEntity.ok().body(searchService.searchConsult(userDetails, keyword));
    }

//    /* 상품 기본 검색 - 인기순 정렬 */
//    @ApiOperation(value = "keyword가 title 혹은 content에 포함되어있는 item을 찾는 검색 메소드 - 인기순 정렬")
//    @PostMapping("items/search/popularity")
//    public ResponseEntity<?> searchItemOrderByPopularity(@AuthenticationPrincipal UserDetails userDetails,
//                                                         @RequestParam("toggle") String toggle, @RequestParam("keyword") String keyword) {
//        return ResponseEntity.ok().body(searchService.searchItemOrderByPopularity(userDetails, toggle, keyword));
//    }
//
//    /* 최근 검색어 */
//    @ApiOperation(value = "최근 검색한 keyword 8개를 불러오는 메소드")
//    @GetMapping("items/search")
//    public ResponseEntity<?> getSearchWord(@AuthenticationPrincipal UserDetails userDetails,
//                                           @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
//        return ResponseEntity.ok().body(searchService.getSearchWord(userDetails, pageable));
//    }
//
//    /* 최근 검색어 개별 삭제 */
//    @ApiOperation(value = "개별 검색 keyword 삭제 메소드")
//    @DeleteMapping("items/search")
//    public ResponseEntity<?> deleteSerachWord(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("searchWord") String searchWord) {
//        searchService.deleteSearchWord(userDetails, searchWord);
//        return ResponseEntity.ok().body(Map.of("msg", "검색 키워드 삭제 완료", "success", true));
//    }
//
//    /* 최근 검색어 전체 삭제 */
//    @ApiOperation(value = "본인이 최근 검색한 keyword 전체 삭제 메소드")
//    @DeleteMapping("items/search/all")
//    public ResponseEntity<?> deleteAllSearchWord(@AuthenticationPrincipal UserDetails userDetails) {
//        searchService.deleteAllSearchWord(userDetails);
//        return ResponseEntity.ok().body(Map.of("msg", "최근 검색어 전체 삭제 완료", "success", true));
//    }
//
//    /* 인기 검색어 */
//    @ApiOperation(value = "가장 빈번하게 검색된 keyword 상위 5개를 불러오는 메소드")
//    @GetMapping("items/search/popularity")
//    public ResponseEntity<?> getPopularSearchWord(@PageableDefault(size = 5)Pageable pageable) {
//        return ResponseEntity.ok().body(searchService.getPopularSearchWord(pageable));
//    }
//
//    /* 검색어 자동완성 */
//    @ApiOperation(value = "검색어 자동완성 메소드")
//    @GetMapping("items/search/auto")
//    public ResponseEntity<?> geKeywordAutomatically(@RequestParam("keyword") String keyword){
//        return ResponseEntity.ok(searchService.getKeywordAutomatically(keyword));
//    }
//
//    /* 현재 토글 상태값 가져오기 */
//    @ApiOperation(value = "현재 토글값 가져오기 메소드")
//    @GetMapping("items/search/toggle")
//    public ResponseEntity<?> getToggleStatus(@AuthenticationPrincipal UserDetails userDetails) {
//        return ResponseEntity.ok().body(searchService.getToggleStatus(userDetails));
////    }
}
