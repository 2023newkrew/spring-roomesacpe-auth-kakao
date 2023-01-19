# Step 1

## 토큰 발급 API

controller
- [x] post api /login/token 시 토큰 발급

service
- [x] memberDAO로 로그인


## 내 정보 조회

- [x] memberController /member/me id값을 토큰을 통해서 가져와 자기정보 조회하기


# Step 2

기능 요구사항
- [x] 예약하기, 예약취소 개선
    - [x] 아래의 API 설계에 맞춰 API 스펙을 변경한다.
    - [x] 비로그인 사용자는 예약이 불가능하다.
    - [x] 자신의 예약이 아닌 경우 예약 취소가 불가능하다.

프로그래밍 요구사항
  - [x] HandlerMethodArgumentResolver를 활용한다.
