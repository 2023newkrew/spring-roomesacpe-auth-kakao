package nextstep.infrastructure.web;

public class UserContextHolder {
    private Long id;

    private UserContextHolder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserContextHolder from(String id) {
        return new UserContextHolder(Long.parseLong(id));
    }
}