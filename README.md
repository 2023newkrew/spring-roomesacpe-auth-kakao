## 기능 요구사항
- [x] 토큰 발급
- [x] 멤버 정보 조회
- [x] 예약 하기
  - 인증된 사용자만 예약 가능
  - 비로그인 사용자는 예약 불가
- [x] 예약 삭제
  - 자신의 예약만 취소 가능

## 사용자 인증 방법
- JWT 기반 인증
- HandlerMethodArgumentResolver를 통해 Controller 계층 메서드 파라미터에 어노테이션으로 HTTP 요청의 헤더에 있는 토큰을 검증, Member 객체를 가져오도록 했습니다.
- 어노테이션 이름은 AuthorizationPrincipal입니다.
- WebMvcConfigurer를 구현해서 리졸버를 등록했습니다.

## 프로젝트 구조 (전체 파일)
- auth
  - AuthController
  - AuthService
  - JwtTokenProvider
  - JwtTokenConfig
  - TokenRequest
  - TokenResponse
- member
  - MemberController
  - MemberService
  - MemberDao
  - Member
  - MemberRequest
- reservation
  - ReservationController
  - ReservationService
  - ReservationDao
  - Reservation
  - ReservationRequest
- schedule
  - ScheduleController
  - ScheduleService
  - ScheduleDao
  - Schedule
  - ScheduleRequest
- theme
  - ThemeController
  - ThemeService
  - ThemeDao
  - Theme
  - ThemeRequest
- support
  - annotation
    - AuthorizationPrincipal
  - exception
    - AuthorizationException
    - ReservationException
    - ScheduleException
    - ThemeException
  - resolver
    - AuthenticationPrincipalArgumentResolver
  - WebMvcCustomConfigurer

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
- AuthE2ETest
  - auth 엔드투엔드 테스트 입니다.
- AuthControllerTest
  - WebMvc 테스트를 경험해보기 위해 Controller에 대해 테스트를 진행했습니다.
    - Controller 계층만 격리해서 테스트해보고 싶어서 다른 계층은 Mock으로 만들었습니다.
- MemberE2ETest
  - 멤버 등록 + 토큰 발급 + 토큰으로 멤버 조회하는 로직을 테스트하는 엔드투엔드 테스트입니다.
- ReservationE2ETest
  - 기존 뼈대 코드의 테스트 로직에 (인증된 사용자만 예약 생성 + 본인의 예약만 삭제 가능) 테스트를 추가했습니다.