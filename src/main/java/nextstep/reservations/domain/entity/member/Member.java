package nextstep.reservations.domain.entity.member;

public class Member {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String phone;

    public Member(final Long id, final String username, final String password, final String name, final String phone) {
        this.id = id;
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

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {
        private Long id;

        private String username;
        private String password;
        private String name;
        private String phone;

        public MemberBuilder id() {
            this.id = id;
            return this;
        }

        public MemberBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public MemberBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        public Member build() {
            return new Member(id, username, password, name, phone);
        }

    }

    public boolean checkWrongPassword(String password) {
        return !this.password.equals(password);
    }
}
