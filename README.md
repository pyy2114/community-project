# Community-project
## 📝프로젝트 주제

회원가입이 된 유저들끼리 자유롭게 의견을 나눌 수 있는 **커뮤니티 게시판** 프로젝트

> 🎯 **목표**: 유저관리, 권한 분리, 게시판 CRUD 기능을 포함한 기본 커뮤니티 기능 구현
>

---

## ⚒️기술 스택

- **Backend**: Kotlin, Spring Boot, Spring security
- **Database**: MySQL, Redis
- **Frontend**: Thymeleaf
- **Build** Tool: Gradle

---

## 💡주요 기능

### 🔷유저

- **회원가입/로그인/로그아웃**: 닉네임은 unique
- **회원 정보 관리**: 닉네임 변경 가능
- **회원 탈퇴**: 사용자 정보 삭제

### 🔷관리자

- **게시글 삭제 가능**: 부적절한 게시글 관리
- **카테고리 관리 가능**: 카테고리 추가/수정/삭제
- **공지글 작성**: 관리자만 작성 가능

### 🔷게시판

- **게시글 CRUD**: 작성, 수정, 조회, 삭제
- **권한 분리**:
    - 유저: 본인 게시글만 수정/삭제 가능
    - 관리자: 타인 게시글 삭제 가능

---
## 💾 ERD 구조

![Image](https://github.com/user-attachments/assets/0c0815be-2b4f-496e-adb0-9e25deb3d81b)
- 2025.03.12: user테이블에 role속성 추가

---

## 📋API 명세서

### 유저

| name | Request Method | Endpoint | Description |
| --- | --- | --- | --- |
| 회원가입 | POST | /user/signup | 아이디, 비밀번호, 닉네임(중복x)을 이용한 회원가입 |
| 로그인 | POST | /user/login | 아이디, 비밀번호를 이용한 로그인 |
| 로그아웃 | POST | /user/logout | 로그인 중이면 로그아웃 |
| 사용자 정보 조회 | GET | /user/mypage | 마이페이지에서 사용자 정보 조회 |
| 사용자 정보 수정 | PATCH | /user/mypage | 마이페이지에서 사용자 정보(닉네임) 수정 |
| 회원탈퇴 | DELETE | /user | 회원 탈퇴 |

### 게시판

| name | Request Method | Endpoint | Description          |
| --- | --- | --- |----------------------|
| 게시글 조회 | GET | /board | 전체 게시글 조회            |
| 게시글 등록 | POST | /board | 게시글 등록(로그인 된 유저만 가능) |
| 게시글 수정 | PATCH | /board/{board_id} | 본인이 작성한 게시글 수정       |
| 게시글 삭제 | DELETE | /board/{board_id} | 본인이 작성한 게시글 삭제       |
| 공지글 작성 | POST | /board | 관리자 권한을 가진 계정만 작성 가능 |

### 카테고리(개발 예정)

| name | Request Method | Endpoint | Description |
| --- | --- | --- | --- |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |
|  |  |  |  |