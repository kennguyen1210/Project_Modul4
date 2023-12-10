package ra.academy.controllers.customer;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.dao.orderDetail.OrderDetailDao;
import ra.academy.dao.product.ProductDao;
import ra.academy.dto.request.UserRequest;
import ra.academy.dto.response.PassChange;
import ra.academy.model.Catalog;
import ra.academy.model.Product;
import ra.academy.model.User;
import ra.academy.service.MailService;
import ra.academy.service.catalog.CatalogService;
import ra.academy.service.order.OrderService;
import ra.academy.service.product.ProductService;
import ra.academy.service.user.UserService;
import ra.academy.validate.ChangePasswordValidate;
import ra.academy.validate.user.UserValidate;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    public static final String MESSAGE = "Thank for use us service! We send you a confirm code, your have 3 times to enter code. If the code is still incorrect after 3 times of entering, it will expire. The code : ";
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidate userValidate;
    @Autowired
    private CatalogDao catalogDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ChangePasswordValidate changePasswordValidate;
    @Autowired
    private MailService mailService;
    @Autowired
    private ProductService productService;
    @RequestMapping("/index")
    public String customer(Model model, HttpSession session){
        model.addAttribute("view","index");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("products",productDao.findBestSeller(10));
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
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
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }

    @RequestMapping("/about")
    public String about(Model model, HttpSession session){
        model.addAttribute("view","about");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/wishList")
    public String wishList(Model model, HttpSession session){
        model.addAttribute("view","wishList");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/catalog")
    public String catalog(Model model, HttpSession session){
        model.addAttribute("view","catalog");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/contact")
    public String contact(Model model, HttpSession session){
        model.addAttribute("view","contact");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/{gen}")
    public String men(Model model, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "12") int size, @RequestParam(name = "gen", defaultValue = "12") boolean gen){
        List<Product> pro = productDao.findByGen(gen);
        model.addAttribute("view","gen");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        model.addAttribute("gen",gen);
        model.addAttribute("products",productService.getListPro(page,size,pro));
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("total_page", new int[productService.getTotalPage(size,pro.size())]);
        return "user/index/index";
    }

    @RequestMapping("/info_detail")
    public String info_detail(Model model, HttpSession session){
        model.addAttribute("view","info_detail");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/update_info")
    public String update_info(Model model, HttpSession session){
        model.addAttribute("view","update_info");
        UserRequest u = userService.createUserRequest((User) session.getAttribute("loginUser"));
        model.addAttribute("user",u);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @PostMapping("/info/update")
    public String updateUser(Model model, HttpSession session, @ModelAttribute("user") UserRequest u,BindingResult bindingResult){
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        userValidate.validate(u,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view","update_info");
            model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
            return "user/index/index";
        }
        User user = userService.createUser(u);
        userService.update(user);
        model.addAttribute("view","info_detail");
        session.setAttribute("loginUser",userService.findById(user.getUserId()));
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        return "user/index/index";
    }
    @RequestMapping("/change_password")
    public String change_password(Model model, HttpSession session){
        model.addAttribute("view","change_password");
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        model.addAttribute("passChange",new PassChange(user.getUserId(),null,null,null));
        return "user/index/index";
    }
    @PostMapping("/password/update")
    public String password_update(Model model, HttpSession session, @ModelAttribute("passChange") PassChange p, BindingResult bindingResult) throws MessagingException {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        changePasswordValidate.validate(p,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view","change_password");
            return "user/index/index";
        }
        model.addAttribute("view","check_confirm");
        session.setAttribute("passChange", p);
        String code = RandomStringUtils.randomAlphabetic(10);
        session.setAttribute("code", code);
        String mess = MESSAGE + code;
        mailService.sendMail("tammaoubqn@gmail.com",user.getEmail(),"Confirm Code",mess);
        return "user/index/index";
    }
    @RequestMapping("/new_code")
    public String getNewCode(Model model, HttpSession session) throws MessagingException {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        String code = RandomStringUtils.randomAlphabetic(10);
        model.addAttribute("view","check_confirm");
        session.setAttribute("code", code);
        String mess = MESSAGE + code;
        mailService.sendMail("tammaoubqn@gmail.com",user.getEmail(),"Confirm Code",mess);
        return "user/index/index";
    }

    @PostMapping("/confirm_code")
    public String confirm_code(@CookieValue(name = "count", required = false) Integer count, HttpServletResponse response, Model model, HttpSession session, @RequestParam(name = "code") String code){
        if(count == null){
            count = 1;
        } else {
            count++;
        }
        Cookie cookie = new Cookie("count",count.toString());
        cookie.setMaxAge(60);//seconds
        response.addCookie(cookie);
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        String c = (String) session.getAttribute("code");
        if(count < 3){
            if(!code.equals(c)){
                model.addAttribute("count",count);
                model.addAttribute("errorMessage","Code incorrect!");
                model.addAttribute("view","check_confirm");
                return "user/index/index";
            }
            PassChange p = (PassChange) session.getAttribute("passChange");
            user.setPassword(p.getNewPassword());
            userService.update(user);
            session.setAttribute("loginUser",user);
            model.addAttribute("message","success");
            model.addAttribute("view","change_complete");
            Cookie userNameCookieRemove = new Cookie("count", "");
            userNameCookieRemove.setMaxAge(0);
            response.addCookie(userNameCookieRemove);
            return "user/index/index";
        } else {
            model.addAttribute("message","failed");
            model.addAttribute("view","change_complete");
            Cookie userNameCookieRemove = new Cookie("count", "");
            userNameCookieRemove.setMaxAge(0);
            response.addCookie(userNameCookieRemove);
            return "user/index/index";
        }
    }
}
