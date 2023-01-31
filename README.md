# 방탈출 인증 관리

## STEP1-2 요구 사항
- [x] 토큰 발급하는 API 생성
- [x] 내 정보 조회하기
  - 토큰을 이용하여 본인 정보 응답하기
- [x] 예약하기, 예약취소 개선
  - 아래의 API 설계에 맞춰 API 스펙 변경한다.
  - 비로그인 사용자는 예약이 불가능하다.
  - 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

## STEP 3 요구 사항
- [ ] 관리자 역할을 추가한다.
  - [ ] 일반 멤버와 관리자 멤버를 구분한다.
- [ ] 관리자 기능을 보호한다.
  - [ ] 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
  - [ ] 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
- [ ] 그 외 관리자 API는 자유롭게 설계하고 적용한다.

## API 설계
- 토큰 발급
  - 요청
    ```
    POST /login/token HTTP/1.1
    Accept: */*
    Content-Type: application/json; charset=UTF-8

    {
      "username": "username",
      "password": "password"
    }
    ```
  - 응답
    ```
    HTTP/1.1 200 
    Content-Type: application/json
    
    {
      "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTEwLCJleHAiOjE2NjMzMDIxMTAsInJvbGUiOiJBRE1JTiJ9.7pxE1cjS51snIrfk21m2Nw0v08HCjgkRD2WSxTK318M"
    }
    ```
- 토큰을 이용해 내 정보 조회
  - 요청
    ```
    GET /members/me HTTP/1.1
    Authorization: token 
    ```
  - 응답
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

- 토큰을 이용한 예약 생성
  - 요청
    ```
    POST /reservations HTTP/1.1
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk4NTkwLCJleHAiOjE2NjMzMDIxOTAsInJvbGUiOiJBRE1JTiJ9.-OO1QxEpcKhmC34HpmuBhlnwhKdZ39U8q91QkTdH9i0
    Content-Type: application/json; charset=UTF-8
    Host: localhost:8080

    {
      // 필요한 값
      // ex) "scheduleId": 1
    }
    ```
  - 응답
    ```
    DELETE /reservations/1 HTTP/1.1
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk5MDcwLCJleHAiOjE2NjMzMDI2NzAsInJvbGUiOiJBRE1JTiJ9.zgz7h7lrKLNw4wP9I0W8apQnMUn3WHnmqQ1N2jNqwlQ
    ```
- 토큰을 이용한 예약 취소
  - 요청
    ```
    DELETE /reservations/1 HTTP/1.1
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYzMjk5MDcwLCJleHAiOjE2NjMzMDI2NzAsInJvbGUiOiJBRE1JTiJ9.zgz7h7lrKLNw4wP9I0W8apQnMUn3WHnmqQ1N2jNqwlQ
    ```
  - 응답
    ```
    HTTP/1.1 204 
    ```
