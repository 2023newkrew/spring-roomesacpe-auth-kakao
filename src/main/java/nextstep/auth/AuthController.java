package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthController(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody TokenRequest request) {
        String password = request.getPassword();
        String username = request.getUsername();

        Member member = memberDao.findByUsername(username);
        String token = jwtTokenProvider.createToken(member.getId().toString());
        TokenResponse response = new TokenResponse(token);
        if (member.getPassword().equals(password)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
