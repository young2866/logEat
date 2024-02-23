<p align='center'>
    <img src="https://capsule-render.vercel.app/api?type=soft&color=ff4500&height=200&section=header&text=Welcome%20to%20LOGEAT%20👋&fontSize=50&animation=fadeIn&fontColor=ffffff"/>
</p>

<p align='center'>
  <a>
    <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/>
  </a>
    <a>
        <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
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

---

## 프로젝트 개요 및 설명

- 프로젝트 개요
  - "Logeat은 '점심에 뭐 먹었어?'라는 호기심에서 시작된 서비스입니다. 동기들의 점심 메뉴가 궁금할 때마다 Logeat를 통해 서로의 식사를 공유하며 궁금증을 쉽게 해결할 수 있습니다."
- 주요 기능
  - 게시 및 공유 📷: 식사의 사진을 찍어 동기들과 공유하세요! 집밥🍚, 식당🍔🍟, 도시락🍱 등 모든 것! 
  - 카테고리 탐색 🔍: 다양한 음식 카테고리를 탐색하세요. 채식부터 육식 인스턴스 까지, 동기분들의 다양한 음식을 찾아보세요.
  - 좋아요 및 팔로우 🧡: 좋아하는 게시물에 좋아요🧡를 누르고, 다른 동기분들을 팔로우하며 최신글을 바로 볼 수 있습니다!
 
- 소프트웨어 아키텍처
<img src="https://github.com/young2866/logEat/assets/122894395/79b373f7-26fd-4669-bfeb-5689ec135adf"/>

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
