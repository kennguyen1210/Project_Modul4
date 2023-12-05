package ra.academy.validate.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.model.Catalog;
import ra.academy.service.catalog.CatalogService;

@Component
public class CatalogAddValidate implements Validator {
    @Autowired
    private CatalogService catalogService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Catalog.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Catalog cat = (Catalog) target;
        if(cat.getCatalogName().isEmpty()){
            errors.rejectValue("catalogName","mess.err.empty","This field cannot be left blank!");
        } else if (catalogService.checkExistByName(cat.getCatalogName())) {
            errors.rejectValue("catalogName","mess.err.exist","The input already exists!");
        }
        if(cat.getDescription().isEmpty()){
            errors.rejectValue("description","mess.err.empty","This field cannot be left blank!");
        }
    }
}
