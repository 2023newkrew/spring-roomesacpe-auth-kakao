package nextstep.global.util;


import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PasswordUtility {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean matches(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }
}