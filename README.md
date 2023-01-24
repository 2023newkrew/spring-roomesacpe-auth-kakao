# Spring 방탈출 인증

## 기능 요구사항
- 인증기능 구현
- [x] 이름과 비밀번호로 토큰을 발급한다.
- [x] 토큰으로 유저 정보를 조회한다.
  - [x] 토큰으로 부터 유저 id를 추출한다.
- 예외 처리
- [x] username, password가 하나라도 잘못되면 로그인이 불가능 하다.
- [x] 인증이 필요한 API 호출시 토큰이 없으면 안된다.

## 추가 요구사항
- 예외처리
- [x] 예약하기 개선
  - [x] 비로그인 사용자는 예약이 불가능하다.
- [x] 예약취소 개선
  - [x] 자신의 예약이 아닌 경우 예약 취소가 불가능 하다.

## step3 기능 요구사항
- 관리자 기능
- [x] 관리자 기능을 보호한다.
  - [x] 관리자 관련 기능 api는 /admin 붙이고 interceptor로 검증한다.
  - [x] 관리자 관련 기능 api는 authorization 헤더를 이용하여 인증과 인가를 진행한다.
- [x] 관리자 역할을 추가한다.
  - [x] 애플리케이션 시작시 관리자가 등록된다.
  - [x] 일반 멤버와 관리자 멤버를 구분한다.
    - [x] 테마 생성, 삭제는 관리자만 할 수 있다.
    - [x] 테마 조회는 일반 멤버도 가능하다.
    - [x] 스케쥴 기능은 관리자만 이용할 수 있다.
    - [x] 예약 기능은 일반 멤버도 이용할 수 있다.
