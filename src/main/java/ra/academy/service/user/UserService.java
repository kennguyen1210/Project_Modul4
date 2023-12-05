package ra.academy.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.user.UserDao;
import ra.academy.dto.request.RegisterForm;
import ra.academy.dto.request.UserRequest;
import ra.academy.model.User;
import ra.academy.service.UploadFileService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UploadFileService uploadFileService;
    @Override
    public int getTotalPage(int size, int len) {
        if(len % size == 0){
            return len / size;
        }
        return (len / size) + 1 ;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userDao.update(user);
    }

    @Override
    public List<User> findAll(int page, int size, String search) {
        List<User> list = userDao.findByUserName(search);
        return list.stream()
                .filter(u->list.indexOf(u)>= page * size)
                .limit(size).collect(Collectors.toList());
    }

    @Override
    public List<User> findNyName(String name) {
        return userDao.findByUserName(name);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public boolean checkExistUserName(String userName) {
        return userDao.getUserByUserName(userName) != null;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.getUserByEmail(email) != null;
    }
    @Override
    public User converFormRegisterToUser(RegisterForm form) {
        String avatar = uploadFileService.uploadFile(form.getAvatar());
        return new User(null,form.getUserName(),form.getEmail(),form.getFullName(),form.getPassword(),false,true, LocalDateTime.now(),avatar);
    }

    @Override
    public User createUser(UserRequest u) {
        String url;
        if(u.getNewAvatar().getSize() == 0){
            url = u.getAvatar();
        } else {
            url = uploadFileService.uploadFile(u.getNewAvatar());
        }
        return new User(u.getUserId(),u.getUserName(),u.getEmail(),u.getFullName(),u.getPassword(),false,true,null,url);
    }

    @Override
    public UserRequest createUserRequest(User u) {
        return new UserRequest(u.getUserId(),u.getUserName(),u.getEmail(),u.getFullName(),null,u.getAvatar(),u.getPassword());
    }
}
