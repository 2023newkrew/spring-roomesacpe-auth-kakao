package nextstep.common;

public class LoginMember {
    private final Long id;
    private final Role role;

    public LoginMember(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
