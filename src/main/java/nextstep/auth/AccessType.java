/**
 * 관리자 권한을 위한 annotation
 */

package nextstep.auth;

import nextstep.member.MemberRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessType {
    MemberRole role();
}
