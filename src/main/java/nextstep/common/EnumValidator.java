package nextstep.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nextstep.member.MemberRole;

public class EnumValidator implements ConstraintValidator<ValidMemberRole, String> {
    private ValidMemberRole memberRoleValidatorAnnotation;

    @Override
    public void initialize(ValidMemberRole constraintAnnotation) {
        memberRoleValidatorAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (MemberRole memberRole : MemberRole.values()) {
            if (value.equals(memberRole.toString())) {
                return true;
            }
        }
        return false;
    }
}
