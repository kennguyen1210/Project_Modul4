package ra.academy.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ra.academy.model.Catalog;
import ra.academy.model.Product;
import ra.academy.model.User;
import ra.academy.service.catalog.CatalogService;
import ra.academy.service.product.ProductService;
import ra.academy.service.user.UserService;

@Controller
@RequestMapping("/api/v4")
public class ApiController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/catalog/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Catalog catalog(@PathVariable Long id){
        return catalogService.findById(id);
    }
    @RequestMapping(value = "/product/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Product product(@PathVariable Long id){
        return productService.findById(id);
    }
    @RequestMapping(value = "/user/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public User user(@PathVariable Long id){
        return userService.findById(id);
    }
}
