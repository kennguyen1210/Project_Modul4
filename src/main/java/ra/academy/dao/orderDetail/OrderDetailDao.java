package ra.academy.dao.orderDetail;

import org.springframework.jdbc.core.JdbcTemplate;
import ra.academy.dto.response.CartList;
import ra.academy.model.OrderDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDetailDao implements IOrderDetailDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartList> findByOrderId(Long id) {
        String sql = "select o.*, p.imageUrl, (o.unitPrice * o.quantity) as total from ordersdetail o join products p on o.productId = p.productId where orderId = ?";
        try {
            List<CartList> list = jdbcTemplate.query(sql, new Object[]{id},(rs, rowNum) -> {
                CartList o = new CartList();
                o.setId(rs.getLong("id"));
                o.setOrderId(rs.getLong("orderId"));
                o.setProductId(rs.getLong("productId"));
                o.setName(rs.getString("name"));
                o.setUnitPrice(rs.getDouble("unitPrice"));
                o.setQuantity(rs.getInt("quantity"));
                o.setTotal(rs.getDouble("total"));
                o.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
                return o;
            });
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    @Override
    public void save(CartList o) {
        String sql = "insert into ordersdetail(orderId,productId,name,unitPrice,quantity) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,o.getOrderId(),o.getProductId(),o.getName(),o.getUnitPrice(),o.getQuantity());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from ordersdetail where id = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void update(CartList o) {
        String sql = "update ordersdetail set quantity = ? where orderId = ? and productId = ?";
        jdbcTemplate.update(sql,o.getQuantity(),o.getOrderId(),o.getProductId());
    }

    @Override
    public CartList findByOrderIdAndProductId(Long orderId, Long productId) {
        String sql = "select o.*, p.imageUrl from ordersdetail o join products p on o.productId = p.productId where o.orderId = ? and o.productId = ?";
        try {
            List<CartList> c = jdbcTemplate.query(sql, new Object[]{orderId,productId},(rs, rowNum) -> {
                CartList o = new CartList();
                o.setId(rs.getLong("id"));
                o.setOrderId(rs.getLong("orderId"));
                o.setProductId(rs.getLong("productId"));
                o.setName(rs.getString("name"));
                o.setUnitPrice(rs.getDouble("unitPrice"));
                o.setQuantity(rs.getInt("quantity"));
                o.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
                return o;
            });
            return c.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        String sql = "update ordersdetail set quantity = ? where id = ?";
        jdbcTemplate.update(sql,quantity,id);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        String sql = "delete from ordersdetail where orderId = ?";
        jdbcTemplate.update(sql,orderId);
    }

    @Override
    public List<CartList> getCart(Long orderId) {
        String sql = "select o.*, p.imageUrl,p.stock as stock, (o.unitPrice * o.quantity) as total from ordersdetail o join products p on o.productId = p.productId where orderId = ?";
        try {
            List<CartList> list = jdbcTemplate.query(sql, new Object[]{orderId},(rs, rowNum) -> {
                CartList o = new CartList();
                o.setId(rs.getLong("id"));
                o.setOrderId(rs.getLong("orderId"));
                o.setProductId(rs.getLong("productId"));
                o.setName(rs.getString("name"));
                o.setUnitPrice(rs.getDouble("unitPrice"));
                o.setQuantity(rs.getInt("quantity"));
                o.setTotal(rs.getDouble("total"));
                o.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
                o.setStock(rs.getInt("stock"));
                return o;
            });
            if(list.isEmpty()){
                return new ArrayList<>();
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
