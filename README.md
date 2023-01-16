# spring-roomescape-auth

## 기능 요구사항
- [X] 토큰 발급하는 API 생성
  - [X] username으로 사용자를 조회하는 기능
  - [x] 등록된 사용자의 password와 요청으로 넘어온 password가 일치하는지 확인하는 기능
  - [X] 사용자에게 accessToken을 발급하는 기능
- [x] 내 정보 조회하기
  - [x] 유효한 토큰인지 검증하는 기능 (Interceptor를 이용해서 구현)
  - [x] 토큰을 이용하여 사용자의 정보를 조회하는 기능 (Resolver를 이용해서 구현)
- [x] 예약하기 API 개선
  - [x] 비로그인 사용자는 예약이 불가능하다.
- [x] 예약취소 API 개선
  - [x] 자신의 예약이 아닌 경우 취소가 불가능하다.

## 프로그래밍 요구사항
- 인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
  - 가능하면 Controller와 인증 로직을 분리한다.
- 토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.
- HandlerMethodArgumentResolver를 활용한다.
