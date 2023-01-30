package nextstep.admin;

import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminDao adminDao;

    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public Boolean isAdmin(Long id) {
        return adminDao.findById(id).getAdmin();
    }
}
