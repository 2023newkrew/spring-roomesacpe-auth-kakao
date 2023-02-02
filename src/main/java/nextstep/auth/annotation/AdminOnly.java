package nextstep.auth.annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import nextstep.member.Role;


@Retention(RetentionPolicy.RUNTIME)
@LoginRequired(requiredRole = Role.ADMIN)
public @interface AdminOnly {
}
