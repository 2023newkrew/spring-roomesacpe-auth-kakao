package nextstep.member.dto;

import java.util.Objects;

public abstract class MemberBaseDto {

    private final Long id;
    private final String username;
    private final String name;

    public MemberBaseDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberBaseDto that = (MemberBaseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name);
    }
}
