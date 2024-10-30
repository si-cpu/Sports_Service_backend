# Sports Service Backend

스포츠 서비스를 위한 백엔드 API 서버입니다. 회원 관리, 게시판, 댓글 기능을 제공합니다.

## 기술 스택

- Java 17
- Spring Boot 3.x
- Gradle 8.5
- MySQL

## API 기능

### 회원 관리 (Member)
- 로그인/로그아웃
- 회원가입
- 회원정보 수정
- 회원 탈퇴
- 카카오 소셜 로그인

### 게시판 (Board)
- 게시글 목록 조회
- 게시글 작성/수정/삭제
- 조회수 기능
- 좋아요 기능

### 댓글 (Reply)
- 댓글 목록 조회
- 댓글 작성/수정/삭제
- 좋아요 기능

### 설치 방법

0. MySQL DB 생성
```mysql
CREATE DATABASE sports_service;
```

1. 프로젝트 클론
```bash
git clone https://github.com/si-cpu/Sports_Service_backend.git
cd Sports_Service_backend
```

2. 빌드
```bash
./gradlew clean build
```

3. 실행
```bash
./gradlew bootRun
```

### 참고
- API 명세서는 [여기](https://docs.google.com/spreadsheets/d/1NjiUIk3EnldoCsNEz8g7pkfvKJjU5qRuFMrqPwF8FX8/edit?gid=873218967#gid=873218967) 에서 확인할 수 있습니다.
- 자세한 실행사항은 application.yml 파일을 참고해주세요.

## ERD (Entity Relationship Diagram)

![Database ERD](./docs/erd.png)

### 테이블 구조

#### User 테이블
- user_key (PK): INT, auto increment
- nick_name: VARCHAR(150), 사용자 닉네임
- pw: VARCHAR(150), 비밀번호
- email: VARCHAR(150), 이메일
- reg_date: DATETIME, 가입일시
- auth: VARCHAR(150), 권한 정보
- login_method: VARCHAR(150), 로그인 방법
- profile: VARCHAR(150), 프로필 정보
- [팀 관련 필드들]: VARCHAR(150), 각 팀 응원 정보

#### Community_Board 테이블
- board_num (PK): INT, auto increment
- user_key (FK): INT, 작성자
- title: VARCHAR(150), 제목
- content: VARCHAR(1000), 내용
- reg_date: DATETIME, 작성일시
- mod_date: DATETIME, 수정일시
- view_count: INT, 조회수
- good_board: INT, 좋아요 수

#### Community_Reply 테이블
- reply_num (PK): INT, auto increment
- board_num (FK): INT, 게시글 번호
- user_key (FK): INT, 작성자
- content: VARCHAR(1000), 댓글 내용
- reg_time: DATETIME, 작성일시
- modify_time: DATETIME, 수정일시
- good_reply: INT, 좋아요 수

#### Like_Board 테이블
- id (PK): INT, auto increment
- user_key (FK): INT, 사용자
- board_num (FK): INT, 게시글 번호

#### Like_Reply 테이블
- id (PK): INT, auto increment
- user_key (FK): INT, 사용자
- reply_num (FK): INT, 댓글 번호


