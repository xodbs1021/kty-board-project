# 🚀 Spring Boot 기반 객체지향 게시판 API 프로젝트

> **세종대학교 인공지능학과 김태윤(kty)** > 카카오 테크 부트캠프(클라우드 과정) 지원 및 백엔드 역량 강화를 위한 포트폴리오 프로젝트입니다.

---

## 📌 프로젝트 개요
단순한 기능 구현을 넘어, **객체지향 설계 원칙(SOLID)**을 준수하고 **JPA 연관관계 최적화**를 학습하기 위해 시작한 프로젝트입니다. 나중에 AWS 환경에 배포하여 트래픽 확장성을 고려한 설계를 목표로 합니다.

## 🛠 Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 4.0.3
- **ORM:** Spring Data JPA
- **Database:** H2 (Dev), MySQL (Planned for Production)
- **Build Tool:** Gradle
- **Etc:** Lombok, Jakarta Validation

## 🏗 System Architecture & Design
### 1. Layered Architecture
- **Controller:** RESTful API 엔드포인트 설계 및 요청 데이터 검증
- **Service:** 트랜잭션 관리 및 핵심 비즈니스 로직(중복 가입 체크 등) 수행
- **Repository:** Spring Data JPA를 활용한 데이터 계층 추상화
- **DTO:** 엔티티 노출을 방지하고 계층 간 데이터 전송 최적화

### 2. Entity Relationship (ERD)
- **Member (1) : Post (N)** - 회원은 여러 게시글을 작성할 수 있습니다.
- **Post (1) : Comment (N)** - 게시글 하나에는 여러 댓글이 달릴 수 있습니다.
- **Member (1) : Comment (N)** - 회원은 여러 댓글을 작성할 수 있습니다.
- 모든 연관관계는 성능 최적화를 위해 **지연 로딩(LAZY Fetch)** 전략을 사용합니다.



## 📝 주요 구현 기능 (Update)
- [x] **회원 관리:** 회원가입, 중복 이메일 검증, 유효성 체크
- [x] **게시판 기능:** 게시글 작성, 수정, 삭제, 전체 목록 조회, 상세 조회
- [x] **댓글 기능:** 게시글별 댓글 작성 및 상세 조회 시 댓글 리스트 포함 응답
- [x] **공통 예외 처리:** `@RestControllerAdvice`를 활용한 전역 에러 핸들링
- [ ] **클라우드 배포:** AWS EC2 및 RDS 연동 (진행 예정)

## 💡 주요 고민 사항 (Retrospective)
- **전역 예외 처리 (Global Exception Handling):** 서비스 곳곳에서 발생하는 예외(존재하지 않는 ID 등)를 한곳에서 처리하여 코드 중복을 제거하고, 클라이언트에게 일관된 에러 응답(JSON)을 제공하도록 설계했습니다.
- **Dirty Checking (변경 감지):** 게시글 수정 시 `save()` 메서드를 명시적으로 호출하는 대신, JPA의 변경 감지 기능을 활용하여 트랜잭션 종료 시점에 업데이트 쿼리가 나가도록 최적화했습니다.
- **RESTful API 설계:** 자원의 상태에 따라 `GET`(조회), `POST`(생성), `PATCH`(수정), `DELETE`(삭제) 메서드를 적절히 활용하여 직관적인 API를 설계했습니다.