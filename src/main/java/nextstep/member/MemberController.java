package nextstep.member;

import lombok.AllArgsConstructor;
import nextstep.auth.AuthService;
import nextstep.member.dto.MemberRequestDto;
import nextstep.member.dto.MemberResponseDto;
import nextstep.support.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequestDto memberRequestDto) {
        Long id = memberService.create(memberRequestDto);
        return ResponseEntity.created(URI.create("/members/" + id))
            .build();
    }

    @GetMapping("/me")
    public ResponseEntity me(@AuthenticationPrincipal String username) {
        MemberResponseDto memberDto = memberService.findByUsername(username);
        return ResponseEntity.ok(memberDto);
    }
}
