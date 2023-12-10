package ra.academy.dao.product;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ra.academy.dto.response.CartList;
import ra.academy.model.Product;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
public class ProductDao implements IProductDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from products order by created_at desc ";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Product p = new Product();
            p.setProductId(rs.getLong("productId"));
            p.setProductName(rs.getString("productName"));
            p.setCatalogId(rs.getLong("catalogId"));
            p.setDescription(rs.getString("description"));
            p.setUnitPrice(rs.getDouble("unitPrice"));
            p.setStock(rs.getInt("stock"));
            p.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
            p.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            p.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
            p.setStatus(rs.getBoolean("status"));
            p.setGen(rs.getBoolean("gen"));
            return p;
        });
    }

    @Override
    public Product findById(Long id) {
        String sql = "select * from products where productId = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public void save(Product product) {
        String sql = "insert into products(productName, description, unitPrice, stock, created_at, imageUrl, status, catalogId, gen) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,product.getProductName(),product.getDescription(),product.getUnitPrice(),product.getStock(), Timestamp.valueOf(product.getCreated_at()),product.getImageUrl(),product.isStatus(),product.getCatalogId(),product.isGen());
    }

    @Override
    public void update(Product product) {
        String sql = "update products set productName = ?, description = ?, unitPrice = ?, stock = ?, imageUrl = ?, status = ?, updated_at = ?, catalogId = ? , gen = ? where productId = ?";
        jdbcTemplate.update(sql,product.getProductName(),product.getDescription(),product.getUnitPrice(),product.getStock(),product.getImageUrl(),product.isStatus(),product.getUpdated_at(),product.getCatalogId(), product.isGen(),product.getProductId());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from products where productId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Product> findByName(String name) {
        String sql = "select * from products where productName like ? order by created_at desc ";
        return jdbcTemplate.query(sql,new Object[]{"%"+name+"%"},(rs, rowNum) -> {
            Product p = new Product();
            p.setProductId(rs.getLong("productId"));
            p.setProductName(rs.getString("productName"));
            p.setCatalogId(rs.getLong("catalogId"));
            p.setDescription(rs.getString("description"));
            p.setUnitPrice(rs.getDouble("unitPrice"));
            p.setStock(rs.getInt("stock"));
            p.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
            p.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            p.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
            p.setStatus(rs.getBoolean("status"));
            p.setGen(rs.getBoolean("gen"));
            return p;
        });
    }

    @Override
    public List<Product> findByCatalogId(Long id) {
        String sql = "select * from products where catalogId = ?";
        return jdbcTemplate.query(sql,new Object[]{id},(rs, rowNum) -> {
            Product p = new Product();
            p.setProductId(rs.getLong("productId"));
            p.setProductName(rs.getString("productName"));
            p.setCatalogId(rs.getLong("catalogId"));
            p.setDescription(rs.getString("description"));
            p.setUnitPrice(rs.getDouble("unitPrice"));
            p.setStock(rs.getInt("stock"));
            p.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
            p.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            p.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
            p.setStatus(rs.getBoolean("status"));
            p.setGen(rs.getBoolean("gen"));
            return p;
        });
    }

    @Override
    public List<Product> findByGen(Boolean gen) {
        String sql = "select * from products where gen = ?";
        return jdbcTemplate.query(sql,new Object[]{gen},(rs, rowNum) -> {
            Product p = new Product();
            p.setProductId(rs.getLong("productId"));
            p.setProductName(rs.getString("productName"));
            p.setCatalogId(rs.getLong("catalogId"));
            p.setDescription(rs.getString("description"));
            p.setUnitPrice(rs.getDouble("unitPrice"));
            p.setStock(rs.getInt("stock"));
            p.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
            p.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            p.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
            p.setStatus(rs.getBoolean("status"));
            p.setGen(rs.getBoolean("gen"));
            return p;
        });
    }

    @Override
    public List<Product> findBestSeller(int size) {
        String sql = "select p.* from products p join ordersdetail ot on p.productId = ot.productId join orders o on ot.orderId = o.orderId group by p.productId order by sum(quantity) desc limit ?";
        return jdbcTemplate.query(sql,new Object[]{size},(rs, rowNum) -> {
            Product p = new Product();
            p.setProductId(rs.getLong("productId"));
            p.setProductName(rs.getString("productName"));
            p.setCatalogId(rs.getLong("catalogId"));
            p.setDescription(rs.getString("description"));
            p.setUnitPrice(rs.getDouble("unitPrice"));
            p.setStock(rs.getInt("stock"));
            p.setImageUrl(Arrays.stream(rs.getString("imageUrl").split(",")).findFirst().orElse(null));
            p.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
            p.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
            p.setStatus(rs.getBoolean("status"));
            p.setGen(rs.getBoolean("gen"));
            return p;
        });
    }

    @Override
    public void updateStock(CartList c) {
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        String sql = "update products set stock = ?,updated_at = ? where productId = ?";
        jdbcTemplate.update(sql,(c.getStock() - c.getQuantity()),date,c.getProductId());
    }
}
