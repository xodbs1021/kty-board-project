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



## 📝 주요 구현 기능
- [x] **회원 관리:** 회원가입, 중복 이메일 검증, 유효성 체크
- [x] **게시판 기능:** 게시글 작성, 전체 목록 조회, 상세 조회(작성자 정보 포함)
- [x] **댓글 기능:** 게시글별 댓글 작성 및 상세 조회 시 댓글 리스트 포함 응답
- [ ] **에러 처리:** Global Exception Handler를 통한 공통 예외 처리 (진행 예정)
- [ ] **클라우드 배포:** AWS EC2 및 RDS 연동 (진행 예정)

## 💡 주요 고민 사항 (Retrospective)
- **DTO 사용 이유:** 엔티티를 직접 API 응답으로 내보낼 때 발생하는 보안 문제와 순환 참조 문제를 해결하기 위해 Response DTO를 설계했습니다.
- **지연 로딩(LAZY):** 다대일 연관관계에서 발생할 수 있는 N+1 문제를 방지하고, 불필요한 조인을 줄여 성능을 최적화했습니다.