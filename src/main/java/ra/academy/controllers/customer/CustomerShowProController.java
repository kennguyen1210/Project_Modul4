package ra.academy.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.dao.product.ProductDao;
import ra.academy.model.Product;
import ra.academy.service.product.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerShowProController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CatalogDao catalogDao;
    @Autowired
    private ProductService productService;
    @RequestMapping("/show_pro/{id}")
    public String showPro(@PathVariable Long id, Model model, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "12") int size, @RequestParam(name = "search", defaultValue = "") String search){
        List<Product> pro = productDao.findByCatalogId(id);
        List<Product> list = productService.getListPro(page,size,pro);
        model.addAttribute("view","showProductByCatalogId");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("products",list);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("search",search);
        model.addAttribute("total_page", new int[productService.getTotalPage(size,pro.size())]);
        return "user/index/index";
    }
    @RequestMapping("/show_all")
    public String show_all(Model model, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "12") int size, @RequestParam(name = "search", defaultValue = "") String search){
        model.addAttribute("view","showAll");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        List<Product> list = productService.findAll(page,size,search);
        List<Product> pro = productDao.findAll();
        model.addAttribute("products",list);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("total_page", new int[productService.getTotalPage(size,pro.size())]);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("search",search);
        return "user/index/index";
    }
    @RequestMapping("/product_detail/{id}")
    public String product_detail(Model model, HttpSession session,@PathVariable Long id){
        Product p = productDao.findById(id);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("view","productDetail");
        model.addAttribute("account",session.getAttribute("loginUser"));
        model.addAttribute("login",session.getAttribute("login"));
        model.addAttribute("product",p);
        model.addAttribute("catalog",catalogDao.findById(p.getCatalogId()));
        return "user/index/index";
    }
    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id, @RequestParam(name = "quantity") int quantity, Model model, HttpSession session){

        return "redirect:/product_detail/"+id;
    }
}
