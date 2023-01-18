# 1단계 - 로그인

## 기능 요구사항

- [x] 토큰 발급하는 API 생성
- [x] 내 정보 조회하기
    - [x] 토큰을 이용하여 본인 정보 응답하기

## 프로그래밍 요구사항

- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
    - 가능하면 Controller와 인증 로직을 분리한다.
- 토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.

## API 설계

### 토큰 발급

```
POST /login/token HTTP/1.1
accept: */*
content-type: application/json; charset=UTF-8

{
    "username": "username",
    "password": "password"
}
```

```
HTTP/1.1 200 
Content-Type: application/json

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTEwLCJleHAiOjE2NjMzMDIxMTAsInJvbGUiOiJBRE1JTiJ9.7pxE1cjS51snIrfk21m2Nw0v08HCjgkRD2WSxTK318M"
}
```

### 내 정보 조회

```
GET /members/me HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
```

```
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

# 2단계 - 로그인 리팩터링

## 기능 요구사항

- [x] 예약하기, 예약취소 개선
    - [x] 아래의 API 설계에 맞춰 API 스펙을 변경한다.
    - [x] 비로그인 사용자는 예약이 불가능하다.
    - [x] 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

## 프로그래밍 요구사항

- [x] HandlerMethodArgumentResolver를 활용한다.

## API 설계

### 예약 생성

```
POST /reservations HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    // 필요한 값
    // ex) "scheduleId": 1
}
```

```
HTTP/1.1 201 Created
Location: /reservations/1
```

### 예약 삭제

```
DELETE /reservations/1 HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk5MDcwLCJleHAiOjE2NjMzMDI2NzAsInJvbGUiOiJBRE1JTiJ9.zgz7h7lrKLNw4wP9I0W8apQnMUn3WHnmqQ1N2jNqwlQ
```

```
HTTP/1.1 204
```

# 3단계 - 관리자 기능

## 기능 요구사항

- [x] 관리자 역할을 추가한다. 
  - [x] 일반 멤버와 관리자 멤버를 구분한다. 
- [x] 관리자 기능을 보호한다. 
  - [x] 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
  - [x] 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
- [x] 그 외 관리자 API는 자유롭게 설계하고 적용한다.

## 프로그래밍 요구사항
- [x] 관리자를 등록하도록 하기 보다는 애플리케이션이 동작할 때 관리자는 추가될 수 있도록 한다

## 요구사항 설명

### 관리자 기능 보호
- 사용자가 사용할 수 있는 기능과 관리자가 사용할 수 있는 기능을 구분한다.
- 예를 들면 아무나 테마를 추가하거나 삭제할 수 있으면 예약 시스템 관리에 문제가 발생할 수 있다.
- 반면에 예약은 관리자가 아니라도 누구나 할 수 있어야 한다.
### 데이터 초기화
- 아래 링크를 참고하여 데이터를 초기화한다
- [Quick Guide on Loading Initial Data with Spring Boot](https://www.baeldung.com/spring-boot-data-sql-and-schema-sql)