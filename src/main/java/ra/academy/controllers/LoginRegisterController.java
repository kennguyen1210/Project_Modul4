package ra.academy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.academy.dto.request.LoginForm;
import ra.academy.dto.request.RegisterForm;
import ra.academy.model.User;
import ra.academy.service.user.UserService;
import ra.academy.validate.login_register.LoginFormValidate;
import ra.academy.validate.login_register.RegisterFormValidate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginRegisterController {
    @Autowired
    private LoginFormValidate loginFormValidate;
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterFormValidate registerFormValidate;
    @RequestMapping
    public String login(Model model, HttpSession session){
        model.addAttribute("loginForm",new LoginForm());
        session.setAttribute("login","not_login");
        return "login/login";
    }
    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpSession session){
        loginFormValidate.validate(form,bindingResult);
        if(bindingResult.hasErrors()){
            return "login/login";
        }
        User u = userService.getUserByUserName(form.getUserName());
        session.setAttribute("loginUser",u);
        session.setAttribute("login","login");
        if(u.isRole()){
            return "redirect:/index";
        }
        return "redirect:/customer/index";
    }
    @PostMapping("/doRegister")
    public String doRegister(@ModelAttribute("registerForm")RegisterForm form,BindingResult bindingResult, Model model){
        registerFormValidate.validate(form,bindingResult);
        if(bindingResult.hasErrors()){
            return "login/register";
        }
        userService.save(userService.converFormRegisterToUser(form));
        return "redirect:/login";
    }
}
