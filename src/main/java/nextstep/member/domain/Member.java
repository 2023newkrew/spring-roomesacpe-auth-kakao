package nextstep.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private final Long id;
    private final String name;
    private final String phone;
    private final String username;
    private String password;

    public final boolean matchPassword(Member target) {

        return PasswordUtil.matches(target.getPassword(), this.password);
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
