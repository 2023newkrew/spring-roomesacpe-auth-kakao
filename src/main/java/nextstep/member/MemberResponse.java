package nextstep.member;

public class MemberResponse {
    private final Long id;
    private final String username;
    private final String name;
    private final String phone;

    public MemberResponse(Long id, String userName, String name, String phone) {
        this.id = id;
        this.username = userName;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
