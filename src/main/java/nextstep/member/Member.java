package nextstep.member;

import org.h2.util.StringUtils;

import static nextstep.support.Messages.EMPTY_VALUE;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member() {
    }

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        checkEmptyValue(username, password);
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public Member(String username, String password, String name, String phone, Role role) {
        checkEmptyValue(username, password);
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    private void checkEmptyValue(String username, String password){
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
            throw new NullPointerException(EMPTY_VALUE.getMessage());
        }
    }
}
