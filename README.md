# step1 

## 기능 요구사항
- 토큰 발급하는 API 생성
- 내 정보 조회하기
  - 토큰을 이용하여 본인 정보 응답하기


## 프로그래밍 요구사항
- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
- 가능하면 Controller와 인증 로직을 분리한다. 
  - 토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.

## API 설계

### 토큰 발급
```http request
POST /login/token HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8

{
    "username": "username",
    "password": "password"
}
```

```http request
HTTP/1.1 200 
Content-Type: application/json

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTEwLCJleHAiOjE2NjMzMDIxMTAsInJvbGUiOiJBRE1JTiJ9.7pxE1cjS51snIrfk21m2Nw0v08HCjgkRD2WSxTK318M"
}
```

### 내 정보 조회

```http request
GET /members/me HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
```

```http request
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "username": "username",
    "password": "password",
    "name": "name",
    "phone": "010-1234-5678"
}
```

## 기능 구현 목록
- [x] AuthController
  - [x] Post mapping "/login/token" 
  - [x] TokenRequest를 받는다.
  - [x] 토큰 생성 로직을 호출한다.
  - [x] TokenResponse 반환 with 코드 200
- [x] AuthService
  - [x] TokenRequest를 받는다.
  - [x] MemberDao에 이메일과 비밀번호를 검증한다.
    - [x] 실패 시 예외를 던진다.
  - [x] JwtTokenProvider를 사용해서 이메일로 토큰을 발급받아서 TokenResponse로 반환
- [x] MemberController
  - [x] Get mapping "/members/me"
  - [x] Authorization 헤더의 값을 읽어서 토큰을 추출한다.
  - [x] JwtTokenProvider의 validateToken()으로 토큰을 검증한다.
    - [x] 만료된 토큰이면 예외를 던진다.
  - [x] 토큰을 복호화해서 이메일을 추출한다.
  - [x] MemberDao의 findByEmail()로 Optional<Member> 객체를 얻는다.
    - [x] 멤버가 존재하지 않으면 예외를 던진다.
  - [x] Member를 반환한다.

---

# step2

## 기능 요구사항
- 예약하기, 예약취소 개선 
  - 아래의 API 설계에 맞춰 API 스펙을 변경한다.
  - 비로그인 사용자는 예약이 불가능하다.
  - 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

## 프로그래밍 요구사항
- `HandlerMethodArgumentResolver`를 활용한다.

## API 설계

### 예약 생성
```http request
POST /reservations HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    // 필요한 값
    // ex) "scheduleId": 1
}
```

```http request
HTTP/1.1 201 Created
Location: /reservations/1
```

### 예약 삭제
```http request
DELETE /reservations/1 HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk5MDcwLCJleHAiOjE2NjMzMDI2NzAsInJvbGUiOiJBRE1JTiJ9.zgz7h7lrKLNw4wP9I0W8apQnMUn3WHnmqQ1N2jNqwlQ
```

```http request
HTTP/1.1 204 
```

## 기능 구현 목록

### 도메인 & DTO 변경
- [x] Reservation 테이블이 member 테이블을 참조(member_id 추가)
- [x] Reservation 도메인 변경
  - [x] String name 삭제
  - [x] Member member 추가
- [x] ReservationRequest 객체 변경
  - [x] String name 삭제
- [x] ReservationResponse 객체 추가
  - [x] Member 객체의 필드를 추출해서 만들기

### 예약하기
- [x] 토큰으로 Member 인증하기
- [x] Member 정보와 함께 예약 저장하기

### 예약 취소
- [x] 토큰으로 Member 인증
- [x] 취소하려는 예약의 소유자인지 확인
  - [x] 아니면 예외 던지기(401 코드)

### 토큰 인증 과정 공통 관심사 분리
- [x] `HandlerMethodArgumentResolver` 와 어노테이션을 활용

---

### 리팩토링 목록
- [x] AuthServiceTest 접근 제어자 수정
- [x] AuthorizationTokenExtractorTest 메서드명 카멜 케이스로 수정
- [x] 토큰 암호화 시 id값 포함하고 인증할 때 토큰의 id를 활용해서 member 정보 조회 
- [x] MemberController 사용하지 않는 코드 제거 
- [x] ExceptionHandler 예외 로그 출력
- [x] 401, 403 코드 구분

---
# step3

## 기능 요구사항
- 관리자 역할을 추가한다.
  - 일반 멤버와 관리자 멤버를 구분한다.
- 관리자 기능을 보호한다.
  - 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
  - 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
  - 그 외 관리자 API는 자유롭게 설계하고 적용한다.

## 프로그래밍 요구사항
- 관리자를 등록하도록 하기 보다는 애플리케이션이 동작할 때 관리자는 추가될 수 있도록 한다

### 기능 구현 목록
- [x] member 스키마에 role 속성 추가, 그에 따른 리팩토링
  - [x] memberDao 수정
  - [x] memberRole enum 타입 추가
- [] /admin/** 경로로 들어오는 요청에 인터셉터 적용
  - [] Admin 검증을 위한 인터셉터 구현
  - [] WebMvcConfiguration에 인터셉터 등록
- [] 테마 테스트 수정 - 어드민 권한 테스트 포함
