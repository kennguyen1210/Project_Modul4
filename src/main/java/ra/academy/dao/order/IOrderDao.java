package ra.academy.dao.order;

import ra.academy.dao.IGenericDao;
import ra.academy.model.Order;

public interface IOrderDao extends IGenericDao<Order,Long> {
    Order findOrderByUserId(Long id);
}
