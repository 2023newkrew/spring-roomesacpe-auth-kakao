## 방탈출 인증 관리

### 구현할 기능 목록
- [x] 토큰을 발급하는 API 구현
- [x] 내 정보를 조회하는 API 구현
  - [x] 발급받은 토큰을 기반으로 정보 응답
- [x] 예약하기, 예약취소 개선
  - [x] 비로그인 사용자는 예약할 수 없다
  - [x] 자신의 예약이 아니라면, 예약을 취소할 수 없다
- [ ] 사용자를 추가하는 로직에서 이름은 고유해야한다
  - [x] DB 스키마에 Unique 제약 조건을 추가한다
  - [ ] Service Layer에서 DB에 넣기전 확인 절차를 거친다

### API 설계
#### [토큰 발급] POST /login/token
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

#### [내 정보 조회] GET /members/me
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

#### [예약 생성] POST /reservations 
```
POST /reservations HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
content-type: application/json; charset=UTF-8
host: localhost:8080

{
    "scheduleId": 1
}
```
```
HTTP/1.1 201 Created
Location: /reservations/1
```

#### [예약 삭제] DELETE /reservations/{reservation-id}
```
DELETE /reservations/1 HTTP/1.1
authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk5MDcwLCJleHAiOjE2NjMzMDI2NzAsInJvbGUiOiJBRE1JTiJ9.zgz7h7lrKLNw4wP9I0W8apQnMUn3WHnmqQ1N2jNqwlQ
```
```
HTTP/1.1 204 
```