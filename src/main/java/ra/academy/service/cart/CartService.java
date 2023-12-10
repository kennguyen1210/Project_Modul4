package ra.academy.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.orderDetail.OrderDetailDao;
import ra.academy.dto.response.CartList;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService{
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Override
    public void addToCart(CartList c) {
        CartList cart = orderDetailDao.findByOrderIdAndProductId(c.getOrderId(),c.getProductId());
        if(cart == null){
            //tao moi
            orderDetailDao.save(c);
        } else {
            // cap nhat quantity
            cart.setQuantity(cart.getQuantity() + c.getQuantity());
            orderDetailDao.update(cart);
        }
    }

    @Override
    public double getTotal(List<CartList> c) {
        return c.stream().map(CartList::getTotal).reduce(0.0,Double::sum);
    }

    @Override
    public boolean checkOverStock(List<CartList> c) {
        return c.stream().anyMatch(u->u.getQuantity() > u.getStock());
    }
}
