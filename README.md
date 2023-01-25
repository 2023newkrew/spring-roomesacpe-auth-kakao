## 기능 요구사항
1,2단계
- [x] 토큰 발급
- [x] 멤버 정보 조회
- [x] 예약 하기
  - 인증된 사용자만 예약 가능
  - 비로그인 사용자는 예약 불가
- [x] 예약 삭제
  - 자신의 예약만 취소 가능
  
  
3단계
- [x] interceptor를 사용한 admin 기능 추가
  - admin 권한이 있어야 가능한 작업 설정
    - 테마 추가, 삭제
    - 스케줄 추가, 삭제

## 사용자 인증 방법
- JWT 기반 인증
- HandlerMethodArgumentResolver를 통해 Controller 계층 메서드 파라미터에 어노테이션으로 HTTP 요청의 헤더에 있는 토큰을 검증, Member 객체를 가져오도록 했습니다.
- 어노테이션 이름은 AuthorizationPrincipal입니다.
- WebMvcConfigurer를 구현해서 리졸버를 등록했습니다.

## 예외처리 방법
- 각 도메인별로 발생할 수 있는 예외 클래스를 만들었고 각 클래스는 RoomEscapeException을 상속하도록 했습니다.
- 발생하는 (예외의 메시지, HTTP 상태코드)를 RoomEscapeExceptionCode 공용체에 선언했습니다.
- GlobalExceptionHandler를 통해 발생하는 RoomEscapeException을 처리하도록 했습니다.

## 프로젝트 구조 (전체 파일)
- admin
  - AdminController
  - AdminInterCeptor
- auth
  - AuthController
  - AuthService
  - JwtTokenProvider
  - JwtTokenConfig
  - JwtTokenExtractor
  - TokenRequest
  - TokenResponse
- member
  - MemberController
  - MemberService
  - MemberDao
  - Member
  - MemberRequest
  - LoginMember
  - LoginMemberResponse
  - Role
- reservation
  - ReservationController
  - ReservationService
  - ReservationDao
  - Reservation
  - ReservationRequest
  - ReservationResponse
- schedule
  - ScheduleController
  - ScheduleService
  - ScheduleDao
  - Schedule
  - ScheduleRequest
  - ScheduleResponse
- theme
  - ThemeController
  - ThemeService
  - ThemeDao
  - Theme
  - ThemeRequest
  - ThemeResponse
- support
  - annotation
    - AuthorizationPrincipal
  - exception
    - RoomEscapeException
      - AuthorizationException
      - ReservationException
      - ScheduleException
      - ThemeException
    - RoomEscapeExceptionCode
  - resolver
    - AuthenticationPrincipalArgumentResolver
  - WebMvcCustomConfigurer
  - GlobalExceptionHandler

## 미션 수행하면서 수정/생성한 파일
- auth
  - AuthController
  - AuthService
  - JwtTokenConfig
  - JwtTokenProvider
- member
  - MemberController
  - MemberService
- reservation
  - ReservationController
  - ReservationService
  - ReservationDao
  - Reservation
- support
  - 전체 파일

## 추가한 테스트
- AdminE2ETest
  - admin 권한이 필요한 URI에 대한 엔드투엔드 테스트 입니다.
- AuthE2ETest
  - auth 엔드투엔드 테스트 입니다.
- AuthControllerTest
  - WebMvc 테스트를 경험해보기 위해 Controller에 대해 테스트를 진행했습니다.
    - Controller 계층만 격리해서 테스트해보고 싶어서 다른 계층은 Mock으로 만들었습니다.
- MemberE2ETest
  - 멤버 등록 + 토큰 발급 + 토큰으로 멤버 조회하는 로직을 테스트하는 엔드투엔드 테스트입니다.
- ReservationE2ETest
  - 기존 뼈대 코드의 테스트 로직에 (인증된 사용자만 예약 생성 + 본인의 예약만 삭제 가능) 테스트를 추가했습니다.