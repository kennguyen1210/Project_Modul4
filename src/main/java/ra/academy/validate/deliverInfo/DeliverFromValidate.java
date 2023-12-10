package ra.academy.validate.deliverInfo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ra.academy.model.DeliverInfo;

@Component
public class DeliverFromValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DeliverInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeliverInfo d = (DeliverInfo) target;
        if(d.getName().isEmpty()){
            errors.rejectValue("name","mess.err.empty","This field cannot be left blank!");
        }
        if(d.getAddress().isEmpty()){
            errors.rejectValue("address","mess.err.empty","This field cannot be left blank!");
        }
        if (d.getPhoneNumber().isEmpty()){
            errors.rejectValue("phoneNumber","mess.err.empty","This field cannot be left blank!");
        } else if (!d.getPhoneNumber().matches("^0[0-9]{9,10}$")){
            errors.rejectValue("phoneNumber","mess.err.regex","Phone is not in correct format ");
        }
    }
}
