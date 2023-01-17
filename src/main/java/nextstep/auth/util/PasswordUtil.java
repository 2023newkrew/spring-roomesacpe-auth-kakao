package nextstep.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {

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
