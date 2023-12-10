package ra.academy.dao.orderDetail;

import ra.academy.dto.response.CartList;

import java.util.List;

public interface IOrderDetailDao {
    List<CartList> findByOrderId(Long id);
    void save(CartList o);
    void delete(Long id);
    void update(CartList o);
    CartList findByOrderIdAndProductId(Long orderId, Long productId);
    void updateQuantity(Long id, int quantity);
    void deleteByOrderId(Long orderId);
    List<CartList> getCart(Long orderId);
}
