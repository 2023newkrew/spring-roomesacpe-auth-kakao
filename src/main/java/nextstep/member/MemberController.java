package nextstep.member;

import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import nextstep.common.AuthenticationPrincipal;
import nextstep.member.dto.MemberRequestDto;
import nextstep.member.dto.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @PostMapping
    public ResponseEntity createMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
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
