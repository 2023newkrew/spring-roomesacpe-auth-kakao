package nextstep.member;

public class MemberMyInfoResponse {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public MemberMyInfoResponse(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public static MemberMyInfoResponse fromEntity(Member member) {
        return new MemberMyInfoResponse(member.getId(), member.getUsername(), member.getPassword(), member.getName(), member.getPhone());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}