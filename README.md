# Sports Service Backend

스포츠 서비스를 위한 백엔드 API 서버입니다. 회원 관리, 게시판, 댓글 기능을 제공합니다.  
서비스 자체는 [여기](https://github.com/si-cpu/Sports_Service_React?tab=readme-ov-file)서 확인하세요
## 기술 스택

- Java 17
- Spring Boot 3.x
- Gradle 8.5
- MySQL 8.0

## 시작하기

### 1. 프로젝트 클론
```
git clone https://github.com/si-cpu/Sports_Service_backend.git
cd Sports_Service_backend
```

### 2. 데이터베이스 설정
1. MySQL에서 데이터베이스 생성
```mysql
CREATE DATABASE sports_service;
```

2. `application.yml` 파일의 데이터베이스 접속 정보 수정

```yaml
spring:
  datasource:
    username: [your_username]  # MySQL 사용자 이름
    password: [your_password]  # MySQL 비밀번호
```

### 3. 실행

```bash
./gradlew bootRun
```

## 문서

자세한 내용은 [Wiki](https://github.com/si-cpu/Sports_Service_backend/wiki)를 참고해주세요:
- 개발 가이드
- API 문서
- 데이터베이스 설계
- 배포 가이드
- 트러블슈팅

## 기여자

- [@minus43](https://github.com/minus43)
- [@yangban0711](https://github.com/yangban0711)
- [@si-cpu](https://github.com/si-cpu)

## 라이선스

이 프로젝트는 [MIT 라이선스](./LICENSE)를 따릅니다.
