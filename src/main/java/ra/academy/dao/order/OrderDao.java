package ra.academy.dao.order;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ra.academy.model.Order;

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
        String sql = "select * from orders where type = true";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Order o = new Order();
            o.setOrderId(rs.getLong("orderId"));
            o.setUserId(rs.getLong("userId"));
            o.setName(rs.getString("name"));
            o.setPhoneNumber(rs.getString("phoneNumber"));
            o.setAddress(rs.getString("address"));
            o.setType(rs.getBoolean("type"));
            o.setTotal(rs.getDouble("total"));
            o.setOrderStatus(rs.getString("orderStatus"));
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
                order.getOrderStatus(), LocalDateTime.now());
    }

    @Override
    public void update(Order order) {
        String sql = "update orders set userId = ?,name = ?,phoneNumber = ?,address = ?,type = ?,total = ?,orderStatus = ?,orderAt = ?, deliverAt = ? where orderId = ?";
        jdbcTemplate.update(sql,order.getUserId(),order.getName(),
                order.getPhoneNumber(),order.getAddress(),
                order.isType(),order.getTotal(),
                order.getOrderStatus(),order.getOrderAt(),order.getDeliverAt(),order.getOrderId());
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
}
