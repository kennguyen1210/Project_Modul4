package ra.academy.service.order;

import ra.academy.model.DeliverInfo;
import ra.academy.model.Order;
import ra.academy.service.IGeneric;

import java.util.List;

public interface IOderService extends IGeneric<Order,Long> {
    Order getCart(Long userId);
    List<Order> findOrderByStatus(String status);
    List<Order> renderOrderList(int page, int size, List<Order> list);
    Order createOrder(Long orderID ,DeliverInfo x, double total);
    List<Order> findAll(int page, int size, String status);
    int getTotalPage(int size, int len);
}
