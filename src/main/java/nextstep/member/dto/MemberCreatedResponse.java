package nextstep.member.dto;

import nextstep.member.Member;

public class MemberCreatedResponse {
    private final Long id;
    private final String username;
    private final String name;
    private final String password;
    private final String phone;

    private MemberCreatedResponse(Long id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public static MemberCreatedResponse of(Member member) {
        return new MemberCreatedResponse(member.getId(), member.getUsername(),
                member.getPassword(), member.getName(), member.getPhone());
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

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
