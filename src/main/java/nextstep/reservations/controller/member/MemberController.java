package nextstep.reservations.controller.member;

import nextstep.reservations.domain.service.member.MemberService;
import nextstep.reservations.dto.member.MemberRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Object> addMember(@RequestBody MemberRequestDto memberRequestDto) {
        Long memberId = memberService.addMember(memberRequestDto);
        return ResponseEntity
                .created(URI.create("/members/" + memberId))
                .build();
    }
}
