package nextstep.member;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String userlevel;

    public Member() {
    }

    public Member(Long id, String username, String password, String name, String phone, String userlevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.userlevel = userlevel;
    }

    public Member(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.userlevel = "member";
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

    public String getUserlevel() {
        return userlevel;
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public MemberResponse toResponse() { return new MemberResponse(id, username, password, name, phone); }
}
