package nextstep.theme;

import nextstep.member.LoginMember;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(LoginMember loginMember, ThemeRequest themeRequest) {
        ThemeValidator.checkRole(loginMember);
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(LoginMember loginMember, Long id) {
        ThemeValidator.checkRole(loginMember);

        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        themeDao.delete(id);
    }
}
