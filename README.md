<p align='center'>
    <img src="https://capsule-render.vercel.app/api?type=soft&color=ff4500&height=200&section=header&text=Welcome%20to%20LOGEAT%20👋&fontSize=50&animation=fadeIn&fontColor=ffffff"/>
</p>

<p align='center'>
  <a>
    <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/>
  </a>
  
  <a>
    <img src="https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vue.js&logoColor=4FC08D"/>
  </a>
  <a>
        <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
    </a>
    <a>
    <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/>
</a>

  <a>
    <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/>
  </a>

<br>
    
  <a>
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  </a>
  <a>
    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  </a>
    
<a>
    <img src="https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink"/>
</a>
  <br>
  <a>
    <img src="https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white"/>
  </a>
  <a>
    <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white"/>
  </a>
  <a>
    <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white"/>
  </a>
<a>
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/>
</a>
  
  <br>

</p> 
<br>


<p align='center'>
  <b>Team: LOGEAT<b>
  <br>
   👨‍💻 김영광, 김종원, 장은지, 박성준, 이종표 
</p>

## 목차
- [개요](#프로젝트-개요-및-설명)
- [프로젝트 협업](#프로젝트-협업)
- [요구사항](#요구사항)
- [API](#api)
- [테스트](#테스트-계획-및-결과-보고서)

---

## 프로젝트 개요 및 설명

- 프로젝트 개요
  - "Logeat은 '점심에 뭐 먹었어?'라는 호기심에서 시작된 서비스입니다. 동기들의 점심 메뉴가 궁금할 때마다 Logeat를 통해 서로의 식사를 공유하며 궁금증을 쉽게 해결할 수 있습니다."
- 주요 기능
  - 게시 및 공유 📷: 식사의 사진을 찍어 동기들과 공유하세요! 집밥🍚, 식당🍔🍟, 도시락🍱 등 모든 것! 
  - 카테고리 탐색 🔍: 다양한 음식 카테고리를 탐색하세요. 채식부터 육식 인스턴스 까지, 동기분들의 다양한 음식을 찾아보세요.
  - 좋아요 및 팔로우 🧡: 좋아하는 게시물에 좋아요🧡를 누르고, 다른 동기분들을 팔로우하며 최신글을 바로 볼 수 있습니다!

- <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"/>

|<a href="https://www.figma.com/file/hmv92aqIxNmIWvNF6dohHn/Figma-basics?type=design&node-id=1669-162202&mode=design&t=BwEeZoLwvMih4yc6-0"><img width="500" hegiht="200" src="https://github.com/young2866/logEat-frontend/assets/122894395/399bec8e-9eb4-420a-9ba3-4729233add80"/></a>|<a href="https://www.figma.com/file/hmv92aqIxNmIWvNF6dohHn/Figma-basics?type=design&node-id=1669-162202&mode=design&t=BwEeZoLwvMih4yc6-0"><img width="500" hegiht="200" src="https://github.com/young2866/logEat-frontend/assets/122894395/e47b08b1-3d84-4438-9b53-2868bc266b4d)"/></a>|
|-----|-----|
|로그인 디자인|메인 디자인|


- 메인 페이지
<a href="http://localhost:8081/main"><img src="https://github.com/young2866/logEat-frontend/assets/122894395/895e7c3b-0e09-4bcc-98fb-dacaedc9cb53"/></a>

- 주요 기능
  - 게시 및 공유 📷: 식사의 사진을 찍어 동기들과 공유하세요! 집밥🍚, 식당🍔🍟, 도시락🍱 등 모든 것! 
  - 카테고리 탐색 🔍: 다양한 음식 카테고리를 탐색하세요. 채식부터 육식 인스턴스 까지, 동기분들의 다양한 음식을 찾아보세요.
  - 좋아요 및 팔로우 🧡: 좋아하는 게시물에 좋아요🧡를 누르고, 다른 동기분들을 팔로우하며 최신글을 바로 볼 수 있습니다!


- 소프트웨어 아키텍처
<img src="https://github.com/young2866/logEat/assets/122894395/79b373f7-26fd-4669-bfeb-5689ec135adf"/>

- 채택한 개발 기술과 브랜치 전략
    - Redis : 메서드의 호출이 비번한 글 상세보기에 경우 캐싱을 하여 Redis에 상세보기 데이터를 저장하고 캐시가 히트될 경우 빠르게 데이터 조회 가능
    - JWT : 액세스 토큰만 사용하는 경우 자주 로그인이 풀리게 됩니다. 하지만 리프레쉬 토큰을 사용하여 자동으로 액세스 토큰을 발급하게 하여 유저 경험을 개선
    - S3 : 프론트엔드에서 받아온 이미지 파일을 S3에 저장하고, url을 반환하여 파일을 S3에 저장하고 S3에서 받아온 url을 기반으로 이미지 처리
    - Scheduler : 유저 상세보기의 경우 글의 조회수를 올려야하는데 상세보기를 할때마다 update 쿼리를 날리게 되면 db서버에 부하가 생기는데 글의 조회수또한 캐싱하여 Scheduler를 통해서 조회수 반영
    - SMTP : 회원가입시에 유저의 이메일 유효성 검사를 하기 위해서 사용
    - 브랜치 전략 : 빠른 프로젝트 적용을 위해서 각자 맡은 기능의 브랜치를 운용을 하고, 기능 구현이 완료가 되면 main 브랜치에 PR을 만들고, 코드리뷰 이후에 바로 merge를 하고 PR이 끝난 브랜치는 삭제했습니다.

- 프로젝트 파일 구조

```
└── main
    ├── java
    │   └── com
    │       └── encore
    │           └── logeat
    │               ├── LogeatApplication.java
    │               ├── common
    │               │   ├── dto
    │               │   │   └── ResponseDto.java
    │               │   ├── entity
    │               │   │   ├── BaseTimeEntity.java
    │               │   │   └── CustomMultipartFile.java
    │               │   ├── exception
    │               │   │   └── GlobalException.java
    │               │   ├── jwt
    │               │   │   ├── JwtAuthenticationFilter.java
    │               │   │   ├── JwtTokenProvider.java
    │               │   │   └── refresh
    │               │   │       ├── UserRefreshToken.java
    │               │   │       └── UserRefreshTokenRepository.java
    │               │   ├── redis
    │               │   │   ├── CacheNames.java
    │               │   │   ├── RedisConfig.java
    │               │   │   └── RedisService.java
    │               │   ├── s3
    │               │   │   └── S3Config.java
    │               │   └── security
    │               │       └── SecurityConfig.java
    │               ├── follow
    │               │   ├── controller
    │               │   │   └── FollowController.java
    │               │   ├── domain
    │               │   │   └── Follow.java
    │               │   ├── repository
    │               │   │   └── FollowRepository.java
    │               │   └── service
    │               │       └── FollowService.java
    │               ├── like
    │               │   ├── Repository
    │               │   │   └── LikeRepository.java
    │               │   ├── controller
    │               │   │   └── LikeController.java
    │               │   ├── domain
    │               │   │   └── Like.java
    │               │   ├── dto
    │               │   └── service
    │               │       └── LikeService.java
    │               ├── mail
    │               │   ├── EmailAuthService.java
    │               │   ├── EmailAuthServiceImpl.java
    │               │   ├── EmailConfig.java
    │               │   ├── controller
    │               │   │   └── MailController.java
    │               │   └── service
    │               │       └── EmailService.java
    │               ├── notification
    │               │   ├── controller
    │               │   │   └── NotificationController.java
    │               │   ├── domain
    │               │   │   ├── Notification.java
    │               │   │   └── NotificationType.java
    │               │   ├── dto
    │               │   │   ├── request
    │               │   │   │   └── NotificationCreateDto.java
    │               │   │   └── response
    │               │   │       └── NotificationListResponseDto.java
    │               │   ├── repository
    │               │   │   └── NotificationRepository.java
    │               │   └── service
    │               │       └── NotificationService.java
    │               ├── post
    │               │   ├── Dto
    │               │   │   ├── RequestDto
    │               │   │   │   ├── PostCreateRequestDto.java
    │               │   │   │   ├── PostSecretUpdateRequestDto.java
    │               │   │   │   └── PostUpdateRequestDto.java
    │               │   │   └── ResponseDto
    │               │   │       ├── PostDetailResponseDto.java
    │               │   │       ├── PostLikeMonthResponseDto.java
    │               │   │       ├── PostLikeWeekResponseDto.java
    │               │   │       └── PostSearchResponseDto.java
    │               │   ├── Service
    │               │   │   └── PostService.java
    │               │   ├── controller
    │               │   │   └── PostController.java
    │               │   ├── domain
    │               │   │   ├── Post.java
    │               │   │   └── PostLikeReport.java
    │               │   └── repository
    │               │       ├── PostLikeReportRepository.java
    │               │       └── PostRepository.java
    │               └── user
    │                   ├── controller
    │                   │   └── UserController.java
    │                   ├── domain
    │                   │   ├── Role.java
    │                   │   └── User.java
    │                   ├── dto
    │                   │   ├── request
    │                   │   │   ├── UserCreateRequestDto.java
    │                   │   │   ├── UserInfoUpdateRequestDto.java
    │                   │   │   └── UserLoginRequestDto.java
    │                   │   └── response
    │                   │       └── UserInfoResponseDto.java
    │                   ├── repository
    │                   │   └── UserRepository.java
    │                   └── service
    │                       └── UserService.java
    └── resources
        ├── application.yml
        └── jwt.yml

```


## 테스트 계획 및 결과 보고서

<details> <summary><b>1. 회원가입 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>1-1. 이메일 인증 테스트</b></summary> 
            <div markdown="1">
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/5e62e753-9fc2-4a45-82d8-3c99206df043"/>
            </div>
        </details>
        <details> <summary><b>1-2. 이메일 인증 테스트</b></summary> 
            <div markdown="1">
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/891a2855-0b27-435d-a7b5-79f55dd6c9f2"/>
            </div>
        </details>        
    </div>
</details>

<details> <summary><b>2. 로그인, 로그아웃 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>2-1. 로그인 테스트</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/51cf6e16-9d11-4aab-b7b1-c215785d5f28"/>
            </div>
        </details>
    </div>
    <div markdown="1"> 
        <details> <summary><b>2-1. 로그아웃 테스트</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/5e06fee8-d261-41e0-97eb-875559a15513"/>
            </div>
        </details>
    </div>
</details>

<details> <summary><b>3. 글 검색 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>3-1. 유저,제목 검색 테스트</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/920ab82b-e770-4431-9276-6951b781ecde"/>
            </div>
        </details>
        <details> <summary><b>3-2. 카테고리 검색 테스트</b></summary> 
            <div markdown="1">
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/323ebcce-0364-45b9-ab22-34453f54318d"/>
            </div>
        </details>
    </div>
</details>

<details> <summary><b>4. 글 쓰기 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>4-1. 글 쓰기 1</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/3d5d0ec2-c2c3-4e59-ad2e-370dc1502cbe">
            </div>
        </details>
        <details> <summary><b>4-2. 글 쓰기 2</b></summary> 
            <div markdown="1">                 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/9aa37372-633e-4488-8c90-07645be50338">
            </div>
        </details>
        <details> <summary><b>4-3. 비밀글 쓰기</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/e2492179-76c2-442f-97a6-781d6d9e018d">
            </div>
        </details>
    </div>
</details>

<details> <summary><b>5. 글 상세보기 테스트</b></summary> 
    <div markdown="1"> 
        <img src="https://github.com/young2866/logEat-frontend/assets/122894395/c913bd5f-3c28-4547-8610-5178aa4a19af"/>
    </div>
</details>

<details> <summary><b>6. 글 좋아요 및 좋아요 취소 테스트</b></summary> 
    <div markdown="1"> 
        <img src="https://github.com/young2866/logEat-frontend/assets/122894395/9e0d3638-a609-435a-8f44-92ed70bcd15f">
    </div>
</details>

<details> <summary><b>7. 유저 팔로우 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>7-1. 팔로우</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/845d48f9-7863-44aa-b0b0-fe24125d67c1">
            </div>
        </details>
    </div>
    <div markdown="1"> 
        <details> <summary><b>7-1. 팔로우 취소</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/7a7ae7f4-2e90-412f-b079-92fccc907321">
            </div>
        </details>
    </div>
</details>

<details> <summary><b>8. 팔로워의 최신글 테스트</b></summary> 
    <div markdown="1"> 
        <img src=""/>
    </div>
</details>

<details> <summary><b>9. 마이페이지 테스트</b></summary> 
    <div markdown="1"> 
        <img src="https://github.com/young2866/logEat-frontend/assets/122894395/c29739be-8bb6-4311-83e2-7ba45300b7be"/>
    </div>
</details>

<details> <summary><b>10. 내가 쓴글 테스트</b></summary> 
    <div markdown="1"> 
        <details> <summary><b>10-1. 내가 쓴글 수정 테스트</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/0d1193b4-008c-4d95-b1a2-b7a6a154fd90"/>
            </div>
        </details>
        <details> <summary><b>10-2. 내가 쓴글 삭제 테스트</b></summary> 
            <div markdown="1"> 
                <img src="https://github.com/young2866/logEat-frontend/assets/122894395/5951f213-81bc-47cb-8055-9e9377f80c03"/>
            </div>
        </details>
    </div>
</details>

<details> <summary><b>11. 팔로우 알림 테스트</b></summary> 
    <div markdown="1"> 
        <img src="https://github.com/young2866/logEat-frontend/assets/122894395/278e9fa3-e07b-47f2-a76b-22233fcab982"/>
    </div>
</details>

<details> <summary><b>12. 좋아요를 가장 많이 받은 글 테스트</b></summary> 
    <div markdown="1"> 
        <img src="https://github.com/young2866/logEat-frontend/assets/122894395/412d0dc2-4c2a-476a-b207-364992e49062"/>
    </div>
</details>

## 프로젝트 협업

<details> <summary> GITHUB </summary> 
<div markdown="1">
  <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/>
<a href="https://github.com/users/young2866/projects/2">
  <img src="https://github.com/young2866/logEat-frontend/assets/122894395/ac8f0566-a29e-49bb-a3b9-023c78ec5a49"/>
</a>
</div>
</details>

<details> <summary> POSTMAN </summary> 
<div markdown="1">
    <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
    <img src="https://github.com/young2866/logEat/assets/122894395/4e69d585-9e58-44d0-8748-7191ac142cf5"/>
    <h3>MockAPI </h3>
    <img width="1372" alt="Postman_MockAPI" src="https://github.com/young2866/logEat/assets/56985486/d8105f3f-82d7-4cb5-ae9d-d77eb0db5a5e">
 
</div>
</details>



## 요구사항
<details> <summary><b>요구사항 명세서</b></summary>   
  <div markdown="1"> 
    <a href="https://docs.google.com/spreadsheets/d/1MMpK2b7POQoquoizv-gYznXUA0YvhHVcbSbb9Tx0hB0/edit#gid=0">
      <img src="https://github.com/young2866/logEat-frontend/assets/122894395/e9842994-60f3-49fd-8d61-ab12b98ded80"/>  
    </a>
  </div>
</details>

<details> <summary><b>개념/논리 모델링에 따른 ERD모델링 및 DB아키텍쳐</b></summary> 
  <div markdown="1"> 
    <br>
    <a href="https://dbdiagram.io/d/65d74f545cd041277492edf0">
    <img src="https://github.com/young2866/logEat-frontend/assets/125184448/a14b3c29-a0c0-4e27-b7f2-0ab7ce33ba4e"/>
  </div>
</details>

## api

<details> <summary><b>API 명세서</b></summary> 
<div markdown="1"> 
    <br>
    <a href="https://www.notion.so/invite/b3a0c77f9d1cf2769fca626c460b796713022126">
    <img width="1350" alt="Screenshot 2024-02-23 at 11 27 17 AM" src="https://github.com/young2866/logEat-frontend/assets/125184448/baf6f7e4-b77f-4af5-8109-a19e2beca25c">
</div>
</details>


## 환경 설정

<details> <summary><b>npm 🔧</b></summary> 
<div markdown="1"> 
  
## Project setup
```
npm install

npm add file:./ckeditor5

npm install @ckeditor/ckeditor5-vue
```


### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
<br/>
<br/>
</div> 
</details>

