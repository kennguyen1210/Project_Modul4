package ra.academy.service.cart;

import ra.academy.dto.response.CartList;

import java.util.List;

public interface ICartService {
    void addToCart(CartList c);
    double getTotal(List<CartList> c);
    boolean checkOverStock(List<CartList> c);
}
