package ra.academy.validate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.dto.request.UserRequest;
import ra.academy.model.User;
import ra.academy.service.user.UserService;

import java.util.List;

@Component
public class UserValidate implements Validator {
    @Autowired
    private UserService userService;
    private final String EMAILREGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest u = (UserRequest) target;
        List<User> list =  userService.findAll();
        if (u.getUserName().isEmpty()){
            errors.rejectValue("userName","mess.err.empty","This field cannot be left blank!");
        } else if (u.getUserName().length() < 6){
            errors.rejectValue("userName","mess.err.minLength","Product name must be at least 6 characters!");
        } else if(!userService.findById(u.getUserId()).getUserName().equals(u.getUserName()) && list.stream().filter(user->user.getUserName().equals(u.getUserName())).findFirst().orElse(null) != null){
            errors.rejectValue("userName","mess.err.exist","UserName exist!");
        }
        if (u.getEmail().isEmpty()){
            errors.rejectValue("email","mess.err.empty","This field cannot be left blank!");
        } else if (!u.getEmail().matches(EMAILREGEX)){
            errors.rejectValue("email","mess.err.regex","Email is not in correct format ");
        } else if(!userService.findById(u.getUserId()).getEmail().equals(u.getEmail()) && list.stream().filter(user->user.getEmail().equals(u.getEmail())).findFirst().orElse(null) != null){
            errors.rejectValue("email","mess.err.exist","UserName exist!");
        }
        if(u.getFullName().isEmpty()){
            errors.rejectValue("fullName","mess.err.empty","This field cannot be left blank!");
        }
    }
}
