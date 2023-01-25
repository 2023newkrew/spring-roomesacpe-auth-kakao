package nextstep.member;

public class MemberResponse {
    private Long id;
    private String username;
    private String name;
    private String phone;

    public MemberResponse() {
    }

    public MemberResponse(Long id, String username, String name, String phone) {
        this.id = id;
        this.username = username;
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
