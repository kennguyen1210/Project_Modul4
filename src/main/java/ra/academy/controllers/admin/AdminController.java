package ra.academy.controllers.admin;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.academy.dto.request.LoginForm;
import ra.academy.dto.request.ProductRequest;
import ra.academy.dto.request.RegisterForm;
import ra.academy.dto.response.UpdateProductRequest;
import ra.academy.model.Catalog;
import ra.academy.model.Product;
import ra.academy.model.User;
import ra.academy.service.catalog.CatalogService;
import ra.academy.service.product.ProductService;
import ra.academy.service.user.UserService;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("")
public class AdminController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @RequestMapping("")
    public String login(Model model){
        model.addAttribute("loginForm",new LoginForm());
        return "login/login";
    }
    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("registerForm",new RegisterForm());
        return "login/register";
    }
    @RequestMapping({"/index"})
    public String home(Model model, HttpSession session){
        model.addAttribute("view","dashboad");
        model.addAttribute("account",session.getAttribute("loginUser"));
        return "admin/index1";
    }
    //catalog
    @RequestMapping("/catalog")
    public String catalog(Model model, @RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "search", defaultValue = "") String search, HttpSession session ){
        model.addAttribute("view","catalog");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        List<Catalog> list =catalogService.findAll(page,size,search);
        model.addAttribute("catalogs",list);
        model.addAttribute("totalPage",new int[catalogService.getTotalPage(size,catalogService.findByName(search).size())]);
        model.addAttribute("search",search);
        return "admin/index1";
    }

    @RequestMapping("/add_form")
    public String addFrom(Model model, HttpSession session){
        model.addAttribute("item",new Catalog());
        model.addAttribute("view","catalog_add");
        model.addAttribute("account",session.getAttribute("loginUser"));
        return "admin/index1";
    }

    @RequestMapping("/edit_form/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session){
        model.addAttribute("catalog",catalogService.findById(id));
        model.addAttribute("view","catalog_edit");
        model.addAttribute("account",session.getAttribute("loginUser"));
        return "admin/index1";
    }
    // product
    @RequestMapping("/products")
    public String product(Model model, @RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "search", defaultValue = "") String search,HttpSession session ){
        model.addAttribute("view","product");
        model.addAttribute("catalogs",catalogService.findAll());
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        List<Product> list = productService.findAll(page,size,search);
        model.addAttribute("products",list);
        model.addAttribute("totalPage",new int[productService.getTotalPage(size, productService.findByName(search).size())]);
        model.addAttribute("search",search);
        return "admin/index1";
    }

    @RequestMapping("/add_product_form")
    public String addProductFrom(Model model, HttpSession session){
        model.addAttribute("productAdd",new ProductRequest());
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("view","product_add");
        model.addAttribute("catalogs",catalogService.findAll());
        return "admin/index1";
    }

    @RequestMapping("/edit_product_form/{id}")
    public String editProductForm(@PathVariable Long id, Model model, HttpSession session){
        Product p = productService.findById(id);
        UpdateProductRequest editP = productService.updatePro(p);
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("productEdit",editP);
        model.addAttribute("view","product_edit");
        model.addAttribute("catalogs",catalogService.findAll());
        return "admin/index1";
    }
    //user
    @RequestMapping("/user")
    public String user(Model model, @RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "search", defaultValue = "") String search, HttpSession session ){
        model.addAttribute("view","user");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        List<User> list = userService.findAll(page,size,search);
        model.addAttribute("users",list);
        model.addAttribute("totalPage",new int[userService.getTotalPage(size,userService.findNyName(search).size())]);
        model.addAttribute("search",search);
        return "admin/index1";
    }
    @RequestMapping("/order")
    public String order(Model model, @RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam(name = "search", defaultValue = "") String search, HttpSession session ){
        model.addAttribute("view","order");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        List<User> list = userService.findAll(page,size,search);
        model.addAttribute("orders",list);
        model.addAttribute("totalPage",userService.getTotalPage(size, list.size()));
        model.addAttribute("search",search);
        return "admin/index1";
    }
}
