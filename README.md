## 요구 사항

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