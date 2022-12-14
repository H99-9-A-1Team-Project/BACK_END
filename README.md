# :mailbox_with_mail: [등대지기] 부동산 안전거래를 위한 안심 상담 플랫폼 

SA : https://docs.google.com/spreadsheets/d/1JBQ1iAl9BINJq8oehd-nAWLhO557RTj0O0C3cUAFJQA/edit#gid=0

## :house_with_garden: 소개 | About Us 

---

## :tada: 서비스 소개


### :question: 집을 구할 때 등기부등본하고...또 뭐 봐야한다고요?

#### :honey_pot: 발품 팔아 알아본 집이 마음에 들었을때!

#### :honey_pot: 대체 무엇을 따져봐야 할지 서툴기만 할 때!

#### :honey_pot: 덜컥 계약하기에는 조금 더 꼼꼼히 알아보고 싶을때!

#### :honey_pot: 공인중개사를 직접 찾아가기엔 질문이 가볍고 모호할 때!    

    

### :lock_with_ink_pen: 등기부등본과 건축물대장에 관련한 소소한 질문에 대해     

### :briefcase: 인증된 공인중개사의 답변을 들을 수 있는 상담서비스 플랫폼입니다.

---

## 📜목차 

### [전체 프로젝트 개발기간](#전체-프로젝트-개발기간)  
### [주요 기능](#주요-기능)
### [기술정보](#기술정보)
---

## :calendar: 전체 프로젝트 개발기간  

:pushpin: 2022. 11.04 ~ 2022. 12.15

## :crossed_swords: 주요 기능   
* 소셜로그인 (카카오톡 로그인)
* 공인중개사와의 부동산 관련문서 해석 상담 요청 
* 공인중개사와의 1:1 채팅 상담
* 상담 내용에 있는 주소 정보를 카카오맵 마커로 시각화
* 의뢰인의 상담 목록 조회 및 검색 기능
* 상담에 대한 별점 만족도 작성 가능
* 공인중개사 프로필 수정 (닉네임, 프로필 사진, 소개메세지)
* 공인중개사가 상담했던 의뢰 목록을 마이페이지에서 무한스크롤로 조회 가능 


## :earth_asia: 기술정보

![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![GitHubActions](https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Figma](https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
![SpringBoot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)    


## :sparkles: 트러블 슈팅

<details>
<summary>리사이징으로 썸네일 이미지 생성</summary>
<div markdown="1">

|||
|--|--|
|요구사항|한번의 Request를 통해 최대 30장 이상의 S3에 요금 부담 절충안 필요|
|선택지|1안) 여러 API로 분할하여 필요시에만 해당 URL을 통해 이미지를 업로드<br>2안) 클라이언트에서 이미지를 압축해서 전달해주기 + S3에 이미지가 업로드 될때마다<br>serverless 프레임워크를 사용해 S3요금 절감|
|의견 조율|프로젝트의 특성상 많은 이미지 데이터를 주고 받아야함과 동시에<br> 많은 요금이 부과될 것을 전망하여 AWS lambda를 도입해 'serverless 프레임워크'를 사용|
|의견 결정|1.AWS Lambda를 사용하여 S3 bucket을 분리<br>2. 리사이징된 썸네일 사진과 원본을 따로 저장하고<br>Lambda에 cloud watch를 통해 이미지 데이터의 송수신을 수시로 모니터링|

</div>
</details>

<details>
<summary>이메일 Sender 로딩 성능 개선</summary>
<div markdown="1">

|||
|--|--|
|요구사항|공인중개사 회원 가입 승인시 java email sender api로 이메일을 보낼 때 대기시간이 긴 이슈 발생|
|선택지|1안) 동보 발송 형태의 단건 전송<br>(connection을 개발자가 직접 연 뒤에 보낼 내용을 한번에 전송하는 방식)<br>2안) 비동기 실행|
|의견 조율|1안 '동보 발송' 형태는 속도 성능 개선의 부분에 있어 좋지만<br>대상자가 많아질수록 스팸처리 되는 부분인 단점이 있을 뿐더러<br>속도도 2안보다 현저히 느리다고 판단함,<br>또한 현 프로젝트에 있어서 다른 내용을 각각의 인원에게 보내야 하는 경우가 아니라고 판단해 2안을 채택하기로 함|
|의견 결정|멀티 쓰레드로 비동기 환경을 구성해 이메일 전송하는 로직과 사용자에게 응답을 보내는 로직을 비동기 실행|
|결과|Jmeter를 이용한 테스트 결과 12.22가량 소요되었던 응답시간이 12ms로 감소|

![KakaoTalk_20221213_175142940](https://user-images.githubusercontent.com/113874252/207718559-f0c7c84d-22b8-44f3-ad07-fd57d550302f.png)

</div>
</details>

<details>
<summary>Query문 검색 기능 성능 개선중</summary>
<div markdown="1">

|||
|--|--|
|요구사항|유저가 최근에 검색했던 검색어 및 자동완성 기능 요구|
|선택지|1안) 새로운 Entity를 생성해 DB에 검색했었던 keyword를 저장하고 자동으로 불러오기<br>2안)Querydsl과 fulltext search로 개선 등 다양한 방법론 해결책을 모색 중|
|의견 조율|현 시점에 배포되어있는 검색 기능 부분에서 속도 개선 위주로 JPA를 사용했었지만<br> 동적 SQL의 다양한 기능을 사용해 유저의 서비스 개선을 고려하여 우선적으로 Query문 개선시도 중|
|의견 결정|현재 JPA Query DSL을 환경설정 후 동적 SQL을 사용하여 유저의 서비스를 위한 다양한 시도를 하는 중<br>또한 유저의 니즈에 맞는 서비스 구현을 위한<br>redis를 이용한 채팅기능 및 fulltext search 기술 도입 고려 중|

</div>
</details>

## :flamingo: 팀원 소개 

|조병민|김성욱|김하나|장경원|조정우|정규재|손하영|
|----|----|----|----|----|----|----|
|FE(팀장)|FE|FE|BE(부팀장)|BE|BE|Designer|
|[:link:](https://github.com/merrybmc)|[:link:](https://github.com/ksu930)|[:link:](https://github.com/superrookie8)|[:link:](https://github.com/Whylano)|[:link:](https://github.com/jjw0611)|[:link:](https://github.com/kyujae-Jung)||
