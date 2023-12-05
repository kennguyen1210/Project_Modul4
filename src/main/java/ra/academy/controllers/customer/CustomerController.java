package ra.academy.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.dao.product.ProductDao;
import ra.academy.dto.request.UserRequest;
import ra.academy.model.Catalog;
import ra.academy.model.User;
import ra.academy.service.catalog.CatalogService;
import ra.academy.service.user.UserService;
import ra.academy.validate.user.UserValidate;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidate userValidate;
    @Autowired
    private CatalogDao catalogDao;
    @Autowired
    private ProductDao productDao;
    @RequestMapping("/index")
    public String customer(Model model, HttpSession session){
        model.addAttribute("view","index");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("products",productDao.findBestSeller(10));
        return "user/index/index";
    }
//    @RequestMapping("/productDetail")
//    public String productDetail(Model model, HttpSession session){
//        model.addAttribute("view","productDetail");
//        model.addAttribute("account",session.getAttribute("loginUser"));
//        model.addAttribute("login",session.getAttribute("login"));
//        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
//        return "user/index/index";
//    }
    @RequestMapping("/showAll")
    public String showAll(Model model, HttpSession session){
        model.addAttribute("view","showAll");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/cart")
    public String cart(Model model, HttpSession session){
        model.addAttribute("view","cart");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/about")
    public String about(Model model, HttpSession session){
        model.addAttribute("view","about");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/wishList")
    public String wishList(Model model, HttpSession session){
        model.addAttribute("view","wishList");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/catalog")
    public String catalog(Model model, HttpSession session){
        model.addAttribute("view","catalog");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/contact")
    public String contact(Model model, HttpSession session){
        model.addAttribute("view","contact");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/men")
    public String men(Model model, HttpSession session){
        model.addAttribute("view","men");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/women")
    public String women(Model model, HttpSession session){
        model.addAttribute("view","women");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/order_complete")
    public String order_complete(Model model, HttpSession session){
        model.addAttribute("view","order_complete");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/checkout")
    public String checkout(Model model, HttpSession session){
        model.addAttribute("view","checkout");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/info_detail")
    public String info_detail(Model model, HttpSession session){
        model.addAttribute("view","info_detail");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/update_info")
    public String update_info(Model model, HttpSession session){
        model.addAttribute("view","update_info");
        UserRequest u = userService.createUserRequest((User) session.getAttribute("loginUser"));
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("user",u);
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @PostMapping("/info/update")
    public String updateUser(Model model, HttpSession session, @ModelAttribute("user") UserRequest u,BindingResult bindingResult){
        model.addAttribute("login",session.getAttribute("login"));
        userValidate.validate(u,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view","update_info");
            model.addAttribute("account",session.getAttribute("loginUser"));
            model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
            return "user/index/index";
        }
        User user = userService.createUser(u);
        userService.update(user);
        model.addAttribute("view","info_detail");
        session.setAttribute("loginUser",userService.findById(user.getUserId()));
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/change_password")
    public String change_password(Model model, HttpSession session){
        model.addAttribute("view","change_password");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
}
