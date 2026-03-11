# 🚀 KTY Java Board Project

Java Spring Boot를 활용한 웹 백엔드 게시판 프로젝트입니다.
단순한 CRUD를 넘어 **사용자 경험(UX)**과 **데이터 무결성**을 고려한 상세 로직들을 구현하는 데 집중했습니다.

---

## 🛠 Tech Stack
* **Framework:** Spring Boot 4.0.3
* **Language:** Java 21
* **Build Tool:** Gradle
* **Database:** MySQL 9.6.0 (Production), H2 (Development)
* **ORM:** Spring Data JPA (Hibernate)
* **View Engine:** Thymeleaf, Bootstrap 5
* **Lombok:** Boilerplate 코드 최소화

---

## ✨ Key Features (주요 기능)

### 1. 사용자 관리 (Member)
* 회원가입 및 로그인 기능 구현
* **HttpSession**을 활용한 세션 기반 인증 처리
* 미로그인 사용자의 게시판 접근 차단 (Redirect 보안 로직)

### 2. 게시판 기능 (Post)
* 게시글 목록 조회 및 상세 보기
* **조회수 중복 증가 방지 로직:** - 목록에서 클릭 시에만 조회수 상승 (`/posts/{id}`)
    - 댓글 등록 후 리다이렉트 시에는 조회수 유지 (`/posts/{id}/view`)
* JPA 연관관계를 활용한 작성자 자동 매핑

### 3. 댓글 시스템 (Comment)
* 게시글 상세 페이지 내 실시간 댓글 등록
* **양방향 연관관계(@OneToMany):** 게시글 삭제 시 관련 댓글 자동 삭제 (Cascade)
* 작성 후 부드러운 화면 전환을 위한 Redirect 처리

### 4. 아키텍처 및 UX 최적화
* **Layered Architecture:** Controller - Service - Repository - Domain(Entity) 구조 준수
* **DTO Pattern:** 데이터 보호 및 전송 효율을 위해 Request/Response DTO 분리
* **History Control:** 댓글 등록 후 '뒤로 가기' 시 이전 데이터 잔상이 남지 않도록 브라우저 히스토리 제어 (JavaScript)

---

## 💡 주요 고민 해결 및 학습 내용 (Troubleshooting)

### ❓ 리다이렉트 시 500 에러 발생
* **원인:** `@RequestMapping` 공통 경로 설정 시 `redirect:board`가 상대 경로로 인식되어 `/api/posts/board`로 접속 시도.
* **해결:** `redirect:/board`와 같이 절대 경로를 명시하여 해결.

### ❓ 상세 페이지 접속 시 JSON 출력 문제
* **원인:** API 컨트롤러(`@ResponseBody`) 주소로 링크가 걸려 있어 순수 데이터만 출력됨.
* **해결:** View 전용 컨트롤러를 분리하고 Thymeleaf 템플릿 주소로 링크 수정.

### ❓ MySQL 연동 이슈 (v9.6.0)
* **내용:** 최신 MySQL 9.6.0 환경에서 `application.yml` 설정을 통해 로컬 DB 연동 및 테이블 자동 생성 확인.

---

## 🚀 How to Run
1. 해당 리포지토리 클론
2. Gradle 빌드 후 `BoardApplication` 실행
3. MySQL 서버 실행 및 `board_db` 생성 필수
4. 브라우저에서 `localhost:8080` 접속