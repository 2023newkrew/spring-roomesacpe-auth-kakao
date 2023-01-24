package nextstep.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminDaoTest {

    @Autowired
    AdminDao adminDao;

    @Test
    void save() {
        Admin admin = new Admin("username", "password", "name", "010-1234-5678");

        Long id = adminDao.save(admin);
        assertThat(id).isNotNull();

        admin.setId(id);
        assertThat(adminDao.findById(id)).isEqualTo(admin);
    }

    @Test
    void findById() {
        Admin admin = new Admin("username", "password", "name", "010-1234-5678");

        Long id = adminDao.save(admin);

        admin.setId(id);
        assertThat(adminDao.findById(id)).isEqualTo(admin);
    }

    @Test
    void findByUsername() {
        String username = "username";
        Admin admin = new Admin(username, "password", "name", "010-1234-5678");

        Long id = adminDao.save(admin);

        admin.setId(id);
        assertThat(adminDao.findByUsername(username)).isNotEmpty().get()
                .isEqualTo(admin);
    }
}