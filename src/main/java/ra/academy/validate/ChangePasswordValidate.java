package ra.academy.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.dao.user.UserDao;
import ra.academy.dto.response.PassChange;
@Component
public class ChangePasswordValidate implements Validator {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean supports(Class<?> clazz) {
        return PassChange.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PassChange p = (PassChange) target;
        String passworld = userDao.findById(p.getId()).getPassword();
        if(p.getOldPassword().isEmpty()){
            errors.rejectValue("oldPassword","mess.err.empty","This field cannot be left blank!");
        } else if (!passworld.equals(p.getOldPassword())) {
            errors.rejectValue("oldPassword","mess.err.notMatch","Old Password not in correct!");
        }
        if (p.getNewPassword().isEmpty()){
            errors.rejectValue("newPassword","mess.err.empty","This field cannot be left blank!");
        } else if (p.getNewPassword().length() < 6){
            errors.rejectValue("newPassword","mess.err.minLength","Password must be lest 6 character!");
        } else if (p.getNewPassword().equals(passworld)) {
            errors.rejectValue("newPassword","mess.err.used","Password has already been used, please enter another password!");
        }
        if (p.getReNewPassword().isEmpty()){
            errors.rejectValue("reNewPassword","mess.err.empty","This field cannot be left blank!");
        } else if (!p.getReNewPassword().equals(p.getNewPassword())) {
            errors.rejectValue("reNewPassword","mess.err.notMatch","Confirm New Password not match with New Password!");
        }

    }
}
