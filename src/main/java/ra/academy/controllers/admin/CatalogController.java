package ra.academy.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.academy.model.Catalog;
import ra.academy.service.catalog.CatalogService;
import ra.academy.validate.catalog.CatalogAddValidate;
import ra.academy.validate.catalog.CatalogEditValidate;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CatalogAddValidate catalogAddValidate;
    @Autowired
    private CatalogEditValidate catalogEditValidate;

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("item") Catalog catalog, BindingResult bindingResult,Model model){
        catalogAddValidate.validate(catalog,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view","catalog_add");
            return "admin/index1";
        }
        Catalog cat = new Catalog(null,catalog.getCatalogName(),catalog.getDescription(), LocalDateTime.now(),true);
        catalogService.save(cat);
        return "redirect:/admin/catalog";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id){
        catalogService.delete(id);
        return "redirect:/admin/catalog";
    }


    @PostMapping("/edit")
    public String doEdit(@ModelAttribute("catalog") Catalog catalog, BindingResult bindingResult, Model model){
        catalogEditValidate.validate(catalog,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("view","catalog_edit");
            return "admin/index1";
        }
        catalogService.update(catalog);
        return "redirect:/admin/catalog";
    }
}
