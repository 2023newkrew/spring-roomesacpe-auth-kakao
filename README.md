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