package ra.academy.validate.login_register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.dto.request.LoginForm;
import ra.academy.model.User;
import ra.academy.service.user.UserService;

import java.util.List;

@Component
public class LoginFormValidate implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm l = (LoginForm) target;
        List<User> u = userService.findAll();
        if(l.getUserName().isEmpty()){
            errors.rejectValue("userName","mess.err.empty","This field cannot be left blank!");
        }
        if(l.getPassword().isEmpty()){
            errors.rejectValue("password","mess.err.empty","This field cannot be left blank!");
        }
        if (u.isEmpty()) {
            errors.rejectValue("userName","mess.err.correct","UserName or Password is correct!");
            errors.rejectValue("password","mess.err.correct","UserName or Password is correct!");
        } else if (u.stream().filter(a->a.getUserName().equals(l.getUserName()) && a.getPassword().equals(l.getPassword()) && a.isStatus()).findFirst().orElse(null) == null) {
            errors.rejectValue("password","mess.err.correct","UserName or Password is correct!");
            errors.rejectValue("userName","mess.err.correct","UserName or Password is correct!");
        }

    }
}
