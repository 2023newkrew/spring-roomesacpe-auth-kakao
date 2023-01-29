package nextstep.member;

public class MemberAuthorizationRequest {
    private Long id;

    public MemberAuthorizationRequest() {

    }

    public MemberAuthorizationRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
