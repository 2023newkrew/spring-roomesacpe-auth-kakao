# Step1

https://edu.nextstep.camp/s/GiKTqpMP/ls/ZuVOiQlB

## 기능 요구사항

- username 중복 불가
    - MemberService에서 중복 확인 및 예외처리
- 토큰 발급하는 API 생성
    - JwtTokenProvider.createToken 수정
        - username을 통해 jwt 생성
- 내 정보 조회하기
    - 토큰을 이용하여 본인 정보 응답하기
    - AuthService에 getUserByToken 생성
    - MemberController가
        - AuthService.getUsernameByToken -> getPrincipal()
        - MemberService.getMemberByUsername

## 프로그래밍 요구사항

인증 로직은 Controller에서 구현하기 보다는 재사용이 용이하도록 분리하여 구현하다.
가능하면 Controller와 인증 로직을 분리한다.
토큰을 이용한 인증 프로세스에 대해 이해가 어려운 경우 페어와 함께 추가학습을 진행한다.
---

# Step2

https://edu.nextstep.camp/s/GiKTqpMP/ls/MlRKpeWQ

## 기능 요구사항

- resolver 이용
    - 존재하지 않는 회원일 때 토큰 발급 실패
    - accessToken 없으면 예외 throw
    - accessToken 복호화 후 username 반환
- 예약하기, 예약취소 개선
    - 아래의 API 설계에 맞춰 API 스펙을 변경한다.
        - 예약 생성
            - requestDto name 삭제
            - resolver 이용
            - scheduleId가 존재하는 지 확인
        - 예약 삭제
            - resolver 이용
            - 자신의 예약인지 확인
            - 예약번호가 유효한 지 확인

## 프로그래밍 요구사항

- HandlerMethodArgumentResolver를 활용한다.

---

# Step3

https://edu.nextstep.camp/s/GiKTqpMP/ls/4djIm1J0

## 상황 설명

- 아무나 테마를 생성하고 관리하면 곤란해진다.
- 관리자 전용 기능의 보호가 필요하다.

## 기능 요구사항

- 관리자 역할을 추가한다.
    - DB 스키마 수정. 멤버 테이블에 role 추가
    - 관리자는 'admin' role로 등록된다.
    - 관리자가 아닌 모든 멤버는 user로 등록된다.
- 관리자 기능을 보호한다.
    - 관리자 관련 기능 API는 /admin 붙이고 interceptor로 검증한다.
    - 관리자 관련 기능 API는 authorization 헤더를 이용하여 인증과 인가를 진행한다.

### admin API (관리자 API)

#### Reservation

    모든 사용자의 예약을 삭제할 수 있다. 
    사용자는 자신의 액세스 토큰이 권한을 가진 예약만을 삭제할 수 있다.

#### Schedule

    스케줄을 생성한다.
    스케줄을 삭제한다.
    사용자에게는 읽기 권한만이 제공된다.

#### Theme

    테마를 생성한다.
    테마를 삭제한다.
    사용자에게는 읽기 권한만이 제공된다.

## 프로그래밍 요구사항

- 관리자를 등록하도록 하기 보다는 애플리케이션이 동작할 때 관리자는 추가될 수 있도록 한다
    - data.sql에 관리자 멤버를 추가하는 쿼리를 추가하는 쿼리를 작성하여 이를 통해서만 관리자 추가 가능
    - 관리자만 또다른 관리자를 추가 가능(admin API)

## TODO
- [x] 일반 멤버 생성시 default로 'user' 역할을 갖는다.
- [x] login 시 관리자인지 확인하여 관리자라면 토큰에 admin role 포함
- [x] jwtToken 생성 시 role 이용 가능
- [x] interceptor 구현
  - [x] 액세스 토큰을 통해 admin 권한을 갖는지 확인한다.
  - [x] admin 권한이 없다면 UnauthorizedException이 발생한다.
- [ ] AdminController 구현
  - [x] Theme
    - [x] 테마 생성 API 이동 (Theme -> Admin) 
    - [x] 테마 삭제 API 이동 (Theme -> Admin)
  - [x] Schedule
    - [x] 스케줄 생성 API 이동(Schedule -> Admin)
    - [x] 스케줄 삭제 API 이동(Schedule -> Admin)
  - [x] Reservation
    - [x] 예약 삭제 API 추가(모든 예약)
