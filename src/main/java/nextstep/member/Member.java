package nextstep.member;

import static nextstep.config.Messages.EMPTY_VALUE;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public Member() {
    }

    public Member(Long id, String username, String password, String name, String phone) {
        checkEmptyValue(username, password);
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public Member(String username, String password, String name, String phone) {
        checkEmptyValue(username, password);
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

    private void checkEmptyValue(String username, String password){
        if (username.isEmpty() || password.isEmpty()) {
            throw new NullPointerException(EMPTY_VALUE.getMessage());
        }
    }
}
