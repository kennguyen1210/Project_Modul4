package ra.academy.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UserRequest {
    private Long userId;
    private String userName;
    private String email;
    private String fullName;
    private MultipartFile newAvatar;
    private String avatar;
    private String password;

    public UserRequest(Long userId, String userName, String email, String fullName, MultipartFile newAvatar, String avatar, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.newAvatar = newAvatar;
        this.avatar = avatar;
        this.password = password;
    }

    public UserRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public MultipartFile getNewAvatar() {
        return newAvatar;
    }

    public void setNewAvatar(MultipartFile newAvatar) {
        this.newAvatar = newAvatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
