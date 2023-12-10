package ra.academy.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.academy.dao.catalog.CatalogDao;
import ra.academy.dao.deliverInfo.DeliverInfoDao;
import ra.academy.dao.orderDetail.OrderDetailDao;
import ra.academy.dao.product.ProductDao;
import ra.academy.dto.response.CartList;
import ra.academy.model.DeliverInfo;
import ra.academy.model.Order;
import ra.academy.model.Product;
import ra.academy.model.User;
import ra.academy.service.DeliverInfoService;
import ra.academy.service.MailService;
import ra.academy.service.cart.CartService;
import ra.academy.service.order.OrderService;
import ra.academy.service.product.ProductService;
import ra.academy.validate.deliverInfo.DeliverFromValidate;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private CartService cartService;
    @Autowired
    private DeliverInfoDao deliverInfoDao;
    @Autowired
    private DeliverFromValidate deliverFromValidate;
    @Autowired
    private DeliverInfoService deliverInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MailService mailService;
    @RequestMapping("/show_pro/{id}")
    public String showPro(@PathVariable Long id, Model model, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "12") int size, @RequestParam(name = "search", defaultValue = "") String search){
        List<Product> pro = productDao.findByCatalogId(id);
        List<Product> list = productService.getListPro(page,size,pro);
        model.addAttribute("view","showProductByCatalogId");
        model.addAttribute("products",list);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("search",search);
        model.addAttribute("total_page", new int[productService.getTotalPage(size,pro.size())]);
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/show_all")
    public String show_all(Model model, HttpSession session, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "12") int size, @RequestParam(name = "search", defaultValue = "") String search){
        model.addAttribute("view","showAll");
        List<Product> list = productService.findAll(page,size,search);
        List<Product> pro = productDao.findByName(search);
        model.addAttribute("products",list);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("total_page", new int[productService.getTotalPage(size,pro.size())]);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("search",search);
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @RequestMapping("/product_detail/{id}")
    public String product_detail(Model model, HttpSession session,@PathVariable Long id){
        Product p = productDao.findById(id);
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        model.addAttribute("view","productDetail");
        model.addAttribute("product",p);
        model.addAttribute("catalog",catalogDao.findById(p.getCatalogId()));
        model.addAttribute("carts",orderDetailDao.findByOrderId((Long) session.getAttribute("orderId")));
        return "user/index/index";
    }
    @PostMapping("/add_to_cart/{id}")
    public String addToCart(@PathVariable Long id, @RequestParam(name = "quantity") int quantity, HttpSession session){
        Product p = productService.findById(id);
        CartList c = new CartList(null,p.getProductId(),(Long)session.getAttribute("orderId"),p.getProductName(), Arrays.stream(p.getImageUrl().split(",")).findFirst().orElse(null),p.getUnitPrice(),quantity);
        cartService.addToCart(c);
        return "redirect:/customer/product_detail/"+id;
    }
    @RequestMapping("/cart")
    public String cart(Model model, HttpSession session){
        model.addAttribute("view","cart");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        List<CartList> list = orderDetailDao.findByOrderId((Long) session.getAttribute("orderId"));
        model.addAttribute("carts",list);
        model.addAttribute("total",cartService.getTotal(list));
        return "user/index/index";
    }
    @RequestMapping("/cart/delete/{id}")
    public String cart_delete(@PathVariable Long id,Model model, HttpSession session){
        orderDetailDao.delete(id);
        return "redirect:/customer/cart";
    }
    @RequestMapping("/cart_change/{id}/{quantity}")
    public String cart_change(@PathVariable Long id,@PathVariable int quantity,Model model, HttpSession session){
        orderDetailDao.updateQuantity(id, quantity);
        return "redirect:/customer/cart";
    }
    @RequestMapping("/checkout")
    public String checkout(Model model, HttpSession session){
        model.addAttribute("view","checkout");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        List<CartList> list = orderDetailDao.findByOrderId((Long) session.getAttribute("orderId"));
        model.addAttribute("carts",list);
        User u = (User) session.getAttribute("loginUser");
        model.addAttribute("total",cartService.getTotal(list));
        model.addAttribute("lists",deliverInfoDao.findByUserId(u.getUserId()));
        model.addAttribute("deliverForm",new DeliverInfo());
        return "user/index/index";
    }
    @RequestMapping("/checkout/{id}")
    public String checkout_change(Model model, HttpSession session, @PathVariable Long id){
        model.addAttribute("view","checkout");
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        List<CartList> list = orderDetailDao.findByOrderId((Long) session.getAttribute("orderId"));
        model.addAttribute("carts",list);
        User u = (User) session.getAttribute("loginUser");
        model.addAttribute("total",cartService.getTotal(list));
        model.addAttribute("lists",deliverInfoDao.findByUserId(u.getUserId()));
        model.addAttribute("deliverForm",deliverInfoDao.findById(id));
        return "user/index/index";
    }
    @RequestMapping("/order_complete")
    public String order_complete(Model model, HttpSession session, @ModelAttribute("deliverForm") DeliverInfo deliverInfo, BindingResult bindingResult) throws MessagingException {
        model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
        User u = (User) session.getAttribute("loginUser");
        List<CartList> list = orderDetailDao.findByOrderId((Long) session.getAttribute("orderId"));
        double total = cartService.getTotal(list);
        deliverFromValidate.validate(deliverInfo,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("carts",list);
            model.addAttribute("total",total);
            model.addAttribute("lists",deliverInfoDao.findByUserId(u.getUserId()));
            model.addAttribute("view","checkout");
            return "user/index/index";
        }
        deliverInfo.setUserId(u.getUserId());
        deliverInfoService.save(deliverInfo);
        // check product of quantity
        List<CartList> overList = orderDetailDao.getCart((Long) session.getAttribute("orderId"));
        if(cartService.checkOverStock(overList)){
            // back to cart view
            model.addAttribute("view","cart");
            model.addAttribute("catalogs",catalogDao.findCatalogHaveProduct());
            model.addAttribute("carts",overList);
            model.addAttribute("total",cartService.getTotal(overList));
            return "user/index/index";
        } else {
            // update quantity at product
            productService.updateStock(overList);
            // update order
            Order o = orderService.createOrder((Long) session.getAttribute("orderId"),deliverInfo,total);
            orderService.update(o);
            mailService.sendMail("tammaoubqn@gmail.com",u.getEmail(),"Order Complete!","Thank you use my service. Your order quick to confirm! Thank you so much!");
            Order newCart = orderService.getCart(u.getUserId());
            model.addAttribute("carts", new ArrayList<>());
            session.setAttribute("orderId", newCart.getOrderId());
            model.addAttribute("view","order_complete");
            return "user/index/index";
        }
    }

}
