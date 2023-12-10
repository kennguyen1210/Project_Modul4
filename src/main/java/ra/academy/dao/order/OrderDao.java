package ra.academy.dao.order;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ra.academy.dto.response.OrderStatus;
import ra.academy.model.Order;
import ra.academy.model.Status;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderDao implements IOrderDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Order> findAll() {
        String sql = "select * from orders where type = true order by orderAt desc ";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Order o = new Order();
            o.setOrderId(rs.getLong("orderId"));
            o.setUserId(rs.getLong("userId"));
            o.setName(rs.getString("name"));
            o.setPhoneNumber(rs.getString("phoneNumber"));
            o.setAddress(rs.getString("address"));
            o.setType(rs.getBoolean("type"));
            o.setTotal(rs.getDouble("total"));
            o.setOrderStatus(Enum.valueOf(Status.class,rs.getString("orderStatus")));
            o.setOrderAt(rs.getTimestamp("orderAt") == null ? null : rs.getTimestamp("orderAt").toLocalDateTime());
            o.setDeliverAt(rs.getTimestamp("deliverAt") == null ? null : rs.getTimestamp("deliverAt").toLocalDateTime());
            return o;
        });
    }

    @Override
    public Order findById(Long id) {
        String sql = "select * from orders where orderId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public void save(Order order) {
        String sql = "insert into orders(userId,name,phoneNumber,address,type,total,orderStatus,orderAt) values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,order.getUserId(),order.getName(),
                order.getPhoneNumber(),order.getAddress(),
                order.isType(),order.getTotal(),
                order.getOrderStatus().name(), null);
    }

    @Override
    public void update(Order order) {
        String sql = "update orders set name = ?,phoneNumber = ?,address = ?,type = ?,total = ?,orderStatus = ?,orderAt = ?, deliverAt = ? where orderId = ?";
        jdbcTemplate.update(sql,order.getName(),
                order.getPhoneNumber(),order.getAddress(),
                order.isType(),order.getTotal(),
                order.getOrderStatus().name(),order.getOrderAt(),order.getDeliverAt(),order.getOrderId());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from orders where orderId = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public Order findOrderByUserId(Long id) {
        String sql = "select * from orders where userId = ? and type = false";
        try {
             Order o = jdbcTemplate.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<>(Order.class));
             return o;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Order> findOderByStatus(String status) {
        String sql = "select * from orders where type = true and orderStatus like ? order by orderAt desc ";
        return jdbcTemplate.query(sql,new Object[]{status},(rs, rowNum) -> {
            Order o = new Order();
            o.setOrderId(rs.getLong("orderId"));
            o.setUserId(rs.getLong("userId"));
            o.setName(rs.getString("name"));
            o.setPhoneNumber(rs.getString("phoneNumber"));
            o.setAddress(rs.getString("address"));
            o.setType(rs.getBoolean("type"));
            o.setTotal(rs.getDouble("total"));
            o.setOrderStatus(Enum.valueOf(Status.class,rs.getString("orderStatus")));
            o.setOrderAt(rs.getTimestamp("orderAt") == null ? null : rs.getTimestamp("orderAt").toLocalDateTime());
            o.setDeliverAt(rs.getTimestamp("deliverAt") == null ? null : rs.getTimestamp("deliverAt").toLocalDateTime());
            return o;
        });
    }

    @Override
    public List<OrderStatus> findQuantityOfStatus() {
        String sql = "select orderStatus, count(orderId) as count from orders group by orderStatus ";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            OrderStatus o = new OrderStatus();
            o.setStatus(Status.valueOf(rs.getString("orderStatus")).name());
            o.setCount(rs.getInt("count"));
            return o;
        });
    }

    @Override
    public void update_status(Long id, String status) {
        String sql = "update orders set orderStatus = ? where orderId = ?";
        jdbcTemplate.update(sql,status,id);
    }
}
