package nextstep.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class MemberForAuth {

    protected final String username;
    protected String password;

    public final boolean matchPassword(String rawPassword) {

        return PasswordUtil.matches(rawPassword, this.password);
    }

    public final void encryptPassword() {
        this.password = PasswordUtil.encrypt(this.password);
    }

    private static final class PasswordUtil {

        private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        private PasswordUtil() {
        }

        public static boolean matches(String rawPassword, String encryptedPassword) {

            return passwordEncoder.matches(rawPassword, encryptedPassword);
        }

        public static String encrypt(String password) {

            return passwordEncoder.encode(password);
        }
    }
}
