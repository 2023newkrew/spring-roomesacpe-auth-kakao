package nextstep.reservations.dto.member;

public class MemberRequestDto {
    private String username;
    private String password;
    private String name;
    private String phone;

    /* RequestBody에서 사용 */
    @SuppressWarnings("unused")
    public MemberRequestDto() {
    }

    public MemberRequestDto(final String username, final String password, final String name, final String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
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
