package nextstep.common;

public enum Role {
    ADMIN() {
        @Override
        public Role upgrade() {
            return Role.ADMIN;
        }

        @Override
        public Role downgrade() {
            return Role.MEMBER;
        }
    },
    MEMBER() {
        @Override
        public Role upgrade() {
            return ADMIN;
        }

        @Override
        public Role downgrade() {
            return MEMBER;
        }
    };

    public abstract Role upgrade();
    public abstract Role downgrade();

}
