package ra.academy.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.academy.dto.request.ProductRequest;
import ra.academy.dto.response.UpdateProductRequest;
import ra.academy.service.product.ProductService;
import ra.academy.validate.product.ProductAddValidate;
import ra.academy.validate.product.ProductEditValidate;

import javax.jws.WebParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductEditValidate productEditValidate;
    @Autowired
    private ProductAddValidate productAddValidate;
    @PostMapping("/add")
    public String doAll(@ModelAttribute("productAdd") ProductRequest pro, BindingResult bindingResult, Model model){
        productAddValidate.validate(pro,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view", "product_add");
            return "admin/index1";
        }
        productService.save(productService.createProduct(pro));
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }
    @PostMapping("/edit")
    public String doEdit(@ModelAttribute("productEdit")UpdateProductRequest p, BindingResult bindingResult, Model model){
        productEditValidate.validate(p,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view", "product_edit");
            return "admin/index1";
        }
        productService.update(productService.updateProduct(p));
        return "redirect:/products";
    }
}
