# 🚀 배달 플랫폼 서비스 (Delivery Platform)

## 개발 기간 : 2024.09~2025.03

## 📝 프로젝트 개요
본 프로젝트는 고객과 음식점, 배달원을 연결하는 **배달 플랫폼 서비스**입니다.  
사용자는 앱을 통해 음식을 주문할 수 있으며, 음식점은 주문을 접수 및 처리하고, 배달원은 실시간으로 배달 요청을 받을 수 있습니다.

이 프로젝트는 **마이크로서비스 아키텍처(MSA)** 와 **RabbitMQ 메시지 큐**를 활용하여 효율적인 주문 관리 및 알림 시스템을 구축하였습니다.

---

## ⚡ 주요 기능

### 🎯 **사용자 기능**
- **회원가입 및 로그인** (JWT 인증)
- **음식 주문 및 결제** (주문 취소 가능)
- **주문 상태 실시간 조회** (SSE 활용)
- **즐겨찾기 음식점 등록**
- **리뷰 및 평점 작성**

### 🏪 **음식점 기능**
- **주문 접수 및 처리**
- **메뉴 관리** (CRUD 기능)
- **배달 요청 및 상태 업데이트**
- **리뷰 관리**

### 🚴‍♂️ **배달원 기능**
- **배달 요청 수락 및 진행**
- **실시간 배달 상태 업데이트**
- **완료된 배달 내역 조회**

### 🔔 **관리자 기능**
- **사용자 및 음식점 관리**
- **배달원 관리**
- **주문 내역 조회 및 통계**
- **매출 분석 및 대시보드 제공**

---
## 🖋️ 개발 내역
- 주문 상태에 따른 흐름 및 실시간 알림 로직을 중심으로 도메인 설계 (User, UserOrder, Store 등)
- 주문 상태에 따라 실시간 알림을 전송하기 위한 SSE 연결 기능 구현 
- 로그인과 인증을 위해 JWT 토큰 기반 인증 로직 적용, Refresh Token은 Redis를 통해 관리
- 주문 완료 후 메시지를 비동기적으로 다른 서비스에 전달하기 위해 RabbitMQ 연동 및 프로듀서/컨슈머 구성
- 클라이언트 요청을 각 서비스로 라우팅하고 인증 처리도 할 수 있도록 API Gateway 설정
- Spring log를 ELK Stack을 활용하여 실시간 모니터링을 구축함


## 🏗️ 아키텍처 설계
![아키텍처](https://github.com/user-attachments/assets/1dbc5de7-7e56-4581-9e6c-7cd846f8baaf)


## 🖋️erd
![image](https://github.com/user-attachments/assets/fb8487c0-6746-466a-b729-e9cfad33d8ee)


### 🔹 **아키텍처 개요**
- **MSA 기반**으로 `API`, `Store-Admin`, `DB`, `Common` 모듈로 구성
- **RabbitMQ**를 활용한 주문/배달 이벤트 메시징 처리
- **Spring Security + JWT**를 통한 인증 및 보안 강화
- **JPA (Spring Data JPA) + MySQL**을 활용한 안정적인 데이터 관리
- SSE (Server-Sent Events)를 활용한 실시간 주문 상태 업데이트



### 🖥 **기술 스택**
| 분야 | 기술 |
|------|------|
| **백엔드** | Java 17, Spring Boot 3, Spring Security, JPA (Hibernate), Kotlin, RabbitMQ |
| **데이터베이스** | MySQL 8, Redis (세션 관리) |
| **메시징** | RabbitMQ |
| **CI/CD** | Docker, GitHub Actions(수정중) |
| **API 문서화** | Swagger (SpringDoc) |
| **프론트엔드** | Thymeleaf|

---

## 🌍각 기능을 학습하고 정리한 블로그 URL
https://velog.io/@olerlmin/RabbitMQ-SSE-%EC%A0%95%EB%A6%AC
https://velog.io/@olerlmin/%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4MSA
https://velog.io/@olerlmin/%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81-f1ly2mwk
