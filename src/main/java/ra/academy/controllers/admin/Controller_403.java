package ra.academy.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/403")
public class Controller_403 {
    @RequestMapping("")
    public String _403_(){
        return "login/403";
    }
}
