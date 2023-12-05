package ra.academy.dao.user;

import ra.academy.dao.IGenericDao;
import ra.academy.model.User;

import java.util.List;

public interface IUserDao extends IGenericDao<User,Long> {
    List<User> findByUserName(String userName);
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
}
