package nextstep.member;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;

    public Member(String username, String password, String name, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = Role.USER;
    }

    public static Member of(MemberResponseDto memberResponseDto) {
        return Member.builder()
                .id(memberResponseDto.getId())
                .username(memberResponseDto.getUsername())
                .password(memberResponseDto.getPassword())
                .phone(memberResponseDto.getPhone())
                .role(memberResponseDto.getRole())
                .build();
    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }

    public boolean checkAdmin(){
        return this.role.equals(Role.ADMIN);
    }
}
