package com.example.planner.database.user;

import com.example.planner.common.base.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class UserRepository implements Repository<User> {

    private final JdbcTemplate jdbc;

    @Override
    public User save(User user) {
        // insert 쿼리를 자동으로 생성해주는 jdbc template
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc);
        insert.withTableName("users").usingGeneratedKeyColumns("user_id");

        Map<String, Object> params = new HashMap<>();
        params.put("user_name", user.getUserName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("created_at", user.getCreatedAt());
        params.put("updated_at", user.getUpdatedAt());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        user.setUserId(id.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> result = jdbc.query(sql, userRowMapper, id);
        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbc.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> update(User user) {
        String sql = "UPDATE users SET user_name = ?, email = ?, password = ? WHERE user_id = ?";
        int updated = jdbc.update(sql, user.getUserName(), user.getEmail(), user.getPassword(), user.getUserId());
        return updated == 1 ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        return jdbc.update(sql, id) == 1;
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> result = jdbc.query(sql, userRowMapper, email);
        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(
                    rs.getLong("user_id"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
}
