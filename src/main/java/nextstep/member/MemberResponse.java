package nextstep.member;

public class MemberResponse {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;

    public MemberResponse (Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.phone = member.getPhone();
    }


    public MemberResponse(String username, String password, String name, String phone) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
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
