package nextstep.member;

import nextstep.auth.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest);
        MemberResponse res = new MemberResponse(id, memberRequest.getUsername(), memberRequest.getPassword(), memberRequest.getName(), memberRequest.getPhone());
        return ResponseEntity.created(URI.create("/members/").resolve(id.toString())).body(res);
    }

    @GetMapping("/me")
    public MemberResponse me(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Member member = memberService.findByToken(token);
        MemberResponse res = new MemberResponse(member.getId(), member.getUsername(), member.getPassword(), member.getName(), member.getPhone());
        return res;
    }
}
