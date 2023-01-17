package nextstep.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LoginMember class is for delivering member information when token is given in header.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginMember {
    private String username;
}
