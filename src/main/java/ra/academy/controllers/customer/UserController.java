package ra.academy.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.academy.model.User;
import ra.academy.service.user.UserService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/lock/{id}")
    public String doLock(@PathVariable Long id){
       User u = userService.findById(id);
       u.setStatus(false);
       u.setUpdatedAt(LocalDateTime.now());
       userService.update(u);
       return "redirect:/user";
    }
    @RequestMapping("/unlock/{id}")
    public String doUnLock(@PathVariable Long id){
        User u = userService.findById(id);
        u.setStatus(true);
        u.setUpdatedAt(LocalDateTime.now());
        userService.update(u);
        return "redirect:/user";
    }
}
