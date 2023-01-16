## 기능 요구사항

- [x] 토큰 발급하는 API 생성
- [ ] 내 정보 조회하기
    - [ ] 토큰을 이용하여 본인 정보 응답하기

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
