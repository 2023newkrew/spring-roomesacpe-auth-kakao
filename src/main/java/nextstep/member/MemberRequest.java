package nextstep.member;

public class MemberRequest {
    private String username;
    private String password;
    private String name;
    private String phone;

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public MemberRequest() {
    }

    public MemberRequest(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getUsername() {
        return username;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public String getPhone() {
        return phone;
    }

    public Member toEntity() {
        return new Member(username, password, name, phone);
    }
}
