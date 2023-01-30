package nextstep.admin;

public class Admin {
    private Long id;
    private Boolean admin;

    public Admin(Long id, Boolean admin) {
        this.id = id;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
