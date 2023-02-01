package nextstep.dto.member;

import nextstep.persistence.member.Member;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

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

    public boolean isNotValid() {
        return isNullOrEmptyOrBlank(username, password, name) || isWrongPhoneNumber(phone);
    }

    private boolean isNullOrEmptyOrBlank(String... values) {
        return Arrays.stream(values)
                .anyMatch(value -> Objects.isNull(value) || value.isEmpty() || value.isBlank());
    }

    private boolean isWrongPhoneNumber(String phone) {
        return !Pattern.matches("^\\d{3}-\\d{3,4}-\\d{4}$", phone);
    }
}
