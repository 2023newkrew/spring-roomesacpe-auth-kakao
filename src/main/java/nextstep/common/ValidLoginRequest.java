package nextstep.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.common.exception.NotExistEntityException;
import nextstep.member.MemberService;
import nextstep.member.dto.MemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLoginRequest.ExistMemberValidator.class)
public @interface ValidLoginRequest {

    String message() default "로그인 정보가 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Component
    class ExistMemberValidator implements ConstraintValidator<ValidLoginRequest, TokenRequestDto> {

        @Autowired
        public MemberService memberService;

        @Override
        public void initialize(ValidLoginRequest constraintAnnotation) {
        }

        @Override
        public boolean isValid(TokenRequestDto tokenRequestDto, ConstraintValidatorContext context) {
            try {
                MemberResponseDto findUser = memberService.findByUsername(tokenRequestDto.getUsername());
                validateUserName(findUser, tokenRequestDto);
                validateRole(findUser, tokenRequestDto);
            } catch (RuntimeException runtimeException) {
                return false;
            }
            return true;
        }

        private void validateUserName(MemberResponseDto memberResponseDto, TokenRequestDto tokenRequestDto) {
            if (!Objects.equals(memberResponseDto.getUsername(), tokenRequestDto.getUsername())) {
                throw new NotExistEntityException();
            }
        }

        private void validateRole(MemberResponseDto memberResponseDto, TokenRequestDto tokenRequestDto) {
            if (!Objects.equals(memberResponseDto.getMemberRole(), tokenRequestDto.getRole())) {
                throw new NotExistEntityException();
            }
        }
    }

}
