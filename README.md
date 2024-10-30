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


