package ra.academy.dao.catalog;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ra.academy.dao.catalog.ICatalogDao;
import ra.academy.model.Catalog;

import java.sql.Timestamp;
import java.util.List;
public class CatalogDao implements ICatalogDao {
    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Catalog> findByName(String name) {
        String sql = "select * from catalogs where catalogName like ?";

        return jdbcTemplate.query(sql, new Object[]{"%"+name+"%"},(rs, rowNum) -> {
            Catalog cat = new Catalog();
            cat.setCatalogId(rs.getLong("catalogId"));
            cat.setCatalogName(rs.getString("catalogName"));
            cat.setDescription(rs.getString("description"));
            cat.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            cat.setStatus(rs.getBoolean("status"));
            return cat;
        });
    }

    @Override
    public List<Catalog> findAll() {
        String sql = "select * from catalogs";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Catalog cat = new Catalog();
            cat.setCatalogId(rs.getLong("catalogId"));
            cat.setCatalogName(rs.getString("catalogName"));
            cat.setDescription(rs.getString("description"));
            cat.setStatus(rs.getBoolean("status"));
            cat.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            return cat;
        });
    }

    @Override
    public Catalog findById(Long id) {
        String sql = "select * from catalogs where catalogId = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<>(Catalog.class));
    }

    @Override
    public void save(Catalog catalog) {
        String sql = "insert into catalogs(catalogName, description, status,createdAt) values (?,?,?,?)";
        jdbcTemplate.update(sql,catalog.getCatalogName(),catalog.getDescription(), catalog.isStatus(),Timestamp.valueOf(catalog.getCreatedAt()));
    }

    @Override
    public void update(Catalog catalog) {
        String sql = "update catalogs set catalogName = ?, description = ?, status = ? where catalogId = ?";
        jdbcTemplate.update(sql, catalog.getCatalogName(),catalog.getDescription(), catalog.isStatus(),catalog.getCatalogId());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from catalogs where catalogId = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public List<Catalog> findCatalogHaveProduct() {
        String sql = "select c.* from catalogs c join products p on c.catalogId = p.catalogId group by c.catalogId";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Catalog cat = new Catalog();
            cat.setCatalogId(rs.getLong("catalogId"));
            cat.setCatalogName(rs.getString("catalogName"));
            cat.setDescription(rs.getString("description"));
            cat.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            cat.setStatus(rs.getBoolean("status"));
            return cat;
        });
    }
}
