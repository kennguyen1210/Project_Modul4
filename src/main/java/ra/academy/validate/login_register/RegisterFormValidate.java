package ra.academy.validate.login_register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.dto.request.RegisterForm;
import ra.academy.model.User;
import ra.academy.service.user.UserService;

import java.util.List;

@Component
public class RegisterFormValidate implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }
    private final String EMAILREGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm form = (RegisterForm) target;
        List<User> list =  userService.findAll();
        if (form.getUserName().isEmpty()){
            errors.rejectValue("userName","mess.err.empty","This field cannot be left blank!");
        } else if (form.getUserName().length() < 6){
            errors.rejectValue("userName","mess.err.minLength","Product name must be at least 6 characters!");
        } else if(list.stream().filter(u->u.getUserName().equals(form.getUserName())).findFirst().orElse(null) != null){
            errors.rejectValue("userName","mess.err.exist","UserName exist!");
        }
        if (form.getEmail().isEmpty()){
            errors.rejectValue("email","mess.err.empty","This field cannot be left blank!");
        } else if (!form.getEmail().matches(EMAILREGEX)){
            errors.rejectValue("email","mess.err.regex","Email is not in correct format ");
        } else if(list.stream().anyMatch(a->a.getEmail().equals(form.getEmail().trim()))){
            errors.rejectValue("email","mess.err.exist","Email exist!");
        }
        if(form.getAvatar().getSize() == 0){
            errors.rejectValue("avatar","mess.err.empty","This field cannot be left blank!");
        }
        if(form.getFullName().isEmpty()){
            errors.rejectValue("fullName","mess.err.empty","This field cannot be left blank!");
        }
        if(form.getPassword().isEmpty()){
            errors.rejectValue("password","mess.err.empty","This field cannot be left blank!");
        } else if (form.getPassword().length() < 6) {
            errors.rejectValue("password","mess.err.minLength","Password must be at least 6 characters!");
        }
        if(!form.getRePassword().equals(form.getPassword())){
            errors.rejectValue("rePassword","mess.err.notMatch","Confirm Password must be the same as the password!");
        }
    }
}
