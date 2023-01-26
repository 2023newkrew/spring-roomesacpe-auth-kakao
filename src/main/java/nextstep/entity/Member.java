package nextstep.entity;

import static nextstep.entity.MemberRole.USER;

import java.util.Objects;

public class Member {
    private Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;
    private final MemberRole role;

    public Member(String username, String password, String name, String phone, MemberRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public static Member giveId(Member member, Long id) {
        member.id = id;
        return member;
    }

    public MemberRole getRole() {
        return role;
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

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {

        private String username;
        private String password;
        private String name;
        private String phone;
        private MemberRole role = USER;

        public MemberBuilder username(String username) {
            this.username = username;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public MemberBuilder role(MemberRole role) {
            this.role = role;
            return this;
        }

        public Member build() {
            validateVariable();
            return new Member(username, password, name, phone,role);
        }

        private void validateVariable() {
            if (Objects.isNull(username)) {
                throw new IllegalArgumentException("username은 Null 일 수 없습니다.");
            }
            if (Objects.isNull(password)) {
                throw new IllegalArgumentException("password은 Null 일 수 없습니다.");

            }
            if (Objects.isNull(name)) {
                throw new IllegalArgumentException("name은 Null 일 수 없습니다.");

            }
            if (Objects.isNull(phone)) {
                throw new IllegalArgumentException("phone은 Null 일 수 없습니다.");
            }
            if (Objects.isNull(role)){
                throw new IllegalArgumentException("role은 Null일 수 없습니다.");
            }
        }

    }

}
