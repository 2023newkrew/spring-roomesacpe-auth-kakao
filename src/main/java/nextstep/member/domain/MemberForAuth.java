package nextstep.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class MemberForAuth {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected final String username;
    protected String password;

    public final boolean matchPassword(String rawPassword) {

        return passwordEncoder.matches(rawPassword, this.password);
    }

    public final void encryptPassword() {
        this.password = encryptPassword(this.password);
    }

    private String encryptPassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }
}
