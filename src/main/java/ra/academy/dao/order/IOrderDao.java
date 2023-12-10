package ra.academy.dao.order;

import ra.academy.dao.IGenericDao;
import ra.academy.dto.response.OrderStatus;
import ra.academy.model.Order;

import java.util.List;

public interface IOrderDao extends IGenericDao<Order,Long> {
    Order findOrderByUserId(Long id);
    List<Order> findOderByStatus(String status);
    List<OrderStatus> findQuantityOfStatus();

    void update_status(Long id, String status);
}
