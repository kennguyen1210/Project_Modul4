package ra.academy.validate.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.dto.request.ProductRequest;
import ra.academy.service.product.ProductService;
@Component
public class ProductAddValidate implements Validator {
    @Autowired
    private ProductService productService;
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductRequest p = (ProductRequest) target;
        if(p.getName().isEmpty()){
            errors.rejectValue("name","mess.err.empty","This field cannot be left blank!");
        } else if (p.getName().length() < 6) {
            errors.rejectValue("name","mess.err.minLength","Product name must be at least 6 characters!");
        } else if (productService.checkExistByName(p.getName())) {
            errors.rejectValue("name","mess.err.exist","The input already exists!");
        }
        if(p.getPrice() <= 0){
            errors.rejectValue("price","mess.err.priceErr","Price must be greater than 0");
        }
        if(p.getStock() < 0){
            errors.rejectValue("stock","mess.err.stockErr","Stock must be greater than or equal to 0");
        }
        if(p.getDes().isEmpty()){
            errors.rejectValue("des","mess.err.empty","This field cannot be left blank!");
        }
        if(p.getImageUrl().get(0).getSize() == 0){
            errors.rejectValue("imageUrl","mess.err.empty","This field cannot be left blank!");
        }
    }
}
