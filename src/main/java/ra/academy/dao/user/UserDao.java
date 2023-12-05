package ra.academy.dao.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ra.academy.dao.user.IUserDao;
import ra.academy.model.User;

import java.sql.Timestamp;
import java.util.List;

public class UserDao implements IUserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getLong("userId"));
            u.setUserName(rs.getString("userName"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setFullName(rs.getString("fullName"));
            u.setRole(rs.getBoolean("role"));
            u.setStatus(rs.getBoolean("status"));
            u.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            u.setUpdatedAt(rs.getTimestamp("updatedAt") == null ? null : rs.getTimestamp("updatedAt").toLocalDateTime());
            u.setAvatar(rs.getString("avatar"));
            return u;
        });
    }

    @Override
    public User findById(Long id) {
        String sql = "select * from users where userId = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User user) {
        String sql = "insert into users(userName,email,fullName, password, role, status, createdAt, avatar) values (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUserName(),user.getEmail(),user.getFullName(),user.getPassword(),user.isRole(),user.isStatus(), Timestamp.valueOf(user.getCreatedAt()),user.getAvatar());
    }

    @Override
    public void update(User user) {
        String sql = "update users set userName = ?,email = ?,fullName = ?, password = ?, role = ?, status = ?, updatedAt = ?, avatar = ? where userId = ?";
        jdbcTemplate.update(sql,user.getUserName(),user.getEmail(),user.getFullName(),user.getPassword(),user.isRole(),user.isStatus(), Timestamp.valueOf(user.getUpdatedAt()),user.getAvatar(),user.getUserId());
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from users where userId = ?";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public List<User> findByUserName(String userName) {
        String sql = "select * from users where fullName like ? and role = 0";
        return jdbcTemplate.query(sql,new Object[]{"%" + userName + "%"},(rs, rowNum) -> {
            User u = new User();
            u.setUserId(rs.getLong("userId"));
            u.setUserName(rs.getString("userName"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setFullName(rs.getString("fullName"));
            u.setRole(rs.getBoolean("role"));
            u.setStatus(rs.getBoolean("status"));
            u.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            u.setUpdatedAt(rs.getTimestamp("updatedAt") == null ? null : rs.getTimestamp("updatedAt").toLocalDateTime());
            u.setAvatar(rs.getString("avatar"));
            return u;
        });
    }

    @Override
    public User getUserByUserName(String userName) {
        String sql = "select * from users where userName like ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName},new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "select * from users where email like ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email},new BeanPropertyRowMapper<>(User.class));
    }
}
