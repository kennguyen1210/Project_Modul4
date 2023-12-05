package ra.academy.service.user;

import ra.academy.dto.request.RegisterForm;
import ra.academy.dto.request.UserRequest;
import ra.academy.model.User;
import ra.academy.service.IGeneric;

import java.util.List;

public interface IUserService extends IGeneric<User, Long> {
    List<User> findAll(int page, int size, String search);
    int getTotalPage(int size, int len);
    List<User> findNyName(String name);
    User getUserByUserName(String userName);
    boolean checkExistUserName(String userName);
    boolean checkExistEmail(String email);
    User converFormRegisterToUser(RegisterForm form);

    User createUser(UserRequest u);
    UserRequest createUserRequest(User u);
}
