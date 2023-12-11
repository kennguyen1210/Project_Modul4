package ra.academy.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.academy.dto.request.ProductRequest;
import ra.academy.dto.response.UpdateProductRequest;
import ra.academy.service.catalog.CatalogService;
import ra.academy.service.product.ProductService;
import ra.academy.validate.product.ProductAddValidate;
import ra.academy.validate.product.ProductEditValidate;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductEditValidate productEditValidate;
    @Autowired
    private ProductAddValidate productAddValidate;
    @Autowired
    private CatalogService catalogService;
    @PostMapping("/add")
    public String doAll(@ModelAttribute("productAdd") ProductRequest pro, BindingResult bindingResult, Model model, HttpSession session){
        productAddValidate.validate(pro,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view", "product_add");
            model.addAttribute("account",session.getAttribute("loginUser"));
            model.addAttribute("catalogs",catalogService.findAll());
            return "admin/index1";
        }
        productService.save(productService.createProduct(pro));
        return "redirect:/admin/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/admin/products";
    }
    @PostMapping("/edit")
    public String doEdit(@ModelAttribute("productEdit")UpdateProductRequest p, BindingResult bindingResult, Model model){
        productEditValidate.validate(p,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view", "product_edit");
            model.addAttribute("catalogs",catalogService.findAll());
            return "admin/index1";
        }
        productService.update(productService.updateProduct(p));
        return "redirect:/admin/products";
    }
}
