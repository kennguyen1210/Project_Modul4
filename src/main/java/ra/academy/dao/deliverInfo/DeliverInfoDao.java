package ra.academy.dao.deliverInfo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ra.academy.model.DeliverInfo;

import java.util.ArrayList;
import java.util.List;

public class DeliverInfoDao implements IDeliverInfoDao{
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DeliverInfo> findAll() {
        return null;
    }

    @Override
    public DeliverInfo findById(Long id) {
        String sql = "select * from deliverinfo where id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(DeliverInfo.class));
    }

    @Override
    public void save(DeliverInfo deliverInfo) {
        String sql = "insert into deliverinfo(userId,name,phoneNumber,address) values(?,?,?,?)";
        jdbcTemplate.update(sql,deliverInfo.getUserId(),deliverInfo.getName(),deliverInfo.getPhoneNumber(),deliverInfo.getAddress());
    }

    @Override
    public void update(DeliverInfo deliverInfo) {
        String sql = "update deliverinfo set name = ?,phoneNumber = ?,address = ? where id = ?";
        jdbcTemplate.update(sql,deliverInfo.getName(),deliverInfo.getPhoneNumber(),deliverInfo.getAddress(),deliverInfo.getId());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<DeliverInfo> findByUserId(Long userId) {
        String sql = "select * from deliverinfo where userId = ?";
        try {
            List<DeliverInfo> list = jdbcTemplate.query(sql,new Object[]{userId},(rs, rowNum) -> {
                DeliverInfo d = new DeliverInfo();
                d.setId(rs.getLong("id"));
                d.setUserId(rs.getLong("userId"));
                d.setName(rs.getString("name"));
                d.setAddress(rs.getString("address"));
                d.setPhoneNumber(rs.getString("phoneNumber"));
                return d;
            });
            if(list.isEmpty()){
                return null;
            }
            return list;
        } catch (Exception e){
            return null;
        }
    }
}
