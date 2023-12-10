package ra.academy.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.academy.dao.order.OrderDao;
import ra.academy.model.DeliverInfo;
import ra.academy.model.Order;
import ra.academy.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOderService{
    @Autowired
    private OrderDao orderDao;
    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public Order getCart(Long userId) {
        Order order = orderDao.findOrderByUserId(userId);
        if(order == null){
            Order o = new Order();
            o.setUserId(userId);
            o.setType(false);
            o.setOrderStatus(Status.WAITING);
            o.setTotal(0.0);
            orderDao.save(o);
            return orderDao.findOrderByUserId(userId);
        }
        return order;
    }

    @Override
    public List<Order> findOrderByStatus(String status) {
        if(status.isEmpty()){
            return orderDao.findAll();
        }
        return orderDao.findOderByStatus(status);
    }

    @Override
    public List<Order> renderOrderList(int page, int size, List<Order> list) {
        return list.stream()
                .filter(cat->list.indexOf(cat) >= page*size)
                .limit(size).collect(Collectors.toList());
    }

    @Override
    public Order createOrder(Long orderId,DeliverInfo x, double total) {
        Order order = orderDao.findById(orderId);
        order.setTotal(total);
        order.setType(true);
        order.setOrderAt(LocalDateTime.now());
        order.setDeliverAt(null);
        order.setAddress(x.getAddress());
        order.setName(x.getName());
        order.setPhoneNumber(x.getPhoneNumber());
        return order;
    }

    @Override
    public List<Order> findAll(int page, int size, String status) {
        List<Order> list;
        if(status.isEmpty()){
            list = orderDao.findAll();
        } else {
            list = orderDao.findOderByStatus(status);
        }
        return list.stream()
                .filter(cat->list.indexOf(cat) >= page*size)
                .limit(size).collect(Collectors.toList());
    }

    @Override
    public int getTotalPage(int size, int len) {
        if(len % size == 0){
            return len / size;
        }
        return (len / size) + 1;
    }
}
