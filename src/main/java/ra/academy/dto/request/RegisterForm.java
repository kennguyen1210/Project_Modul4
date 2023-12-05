package ra.academy.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class RegisterForm {
    private String userName;
    private String email;
    private String password;
    private String rePassword;
    private MultipartFile avatar;
    private String fullName;

    public RegisterForm(String userName, String email, String password, String rePassword, MultipartFile avatar, String fullName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.avatar = avatar;
        this.fullName = fullName;
    }

    public RegisterForm() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
