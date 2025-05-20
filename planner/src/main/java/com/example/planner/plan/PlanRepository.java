package com.example.planner.plan;

import com.example.planner.common.base.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class PlanRepository implements Repository<Plan> {

    private final JdbcTemplate jdbc;

    @Override
    public Plan save(Plan plan) {
        // insert 쿼리를 자동으로 생성해주는 jdbc template
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc);
        insert.withTableName("plan").usingGeneratedKeyColumns("plan_id");

        Map<String, Object> params = new HashMap<>();
        params.put("plan_text", plan.getPlanText());
        params.put("user_name", plan.getUserName());
        params.put("password", plan.getPassword());
        params.put("created_at", plan.getCreatedAt());
        params.put("updated_at", plan.getUpdatedAt());

        // 여기서 에러!! 리턴 Number to Long에서 오류
        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        plan.setPlanId(id.longValue());
        return plan;
    }

    @Override
    public Optional<Plan> findById(Long id) {
        String sql = "SELECT * FROM plan WHERE plan_id = ?";
        List<Plan> result = jdbc.query(sql, planRowMapper, id);
        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public List<Plan> findAll() {
        String sql = "SELECT * FROM plan";
        return jdbc.query(sql, planRowMapper);
    }

    @Override
    public Optional<Plan> update(Long id, Plan plan) {
        String sql = "UPDATE plan SET plan_text = ?, user_name = ?, password = ?, updated_at = ? WHERE plan_id = ?";
        int updated = jdbc.update(sql, plan.getPlanText(), plan.getUserName(), plan.getPassword(), plan.getUpdatedAt(), plan.getPlanId());
        return updated == 1 ? Optional.of(plan) : Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM plan WHERE plan_id = ?";
        return jdbc.update(sql, id) == 1;
    }

    // custom query
    public List<Plan> findAll(String userName, LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM plan WHERE user_name = ? AND updated_at >= ? AND updated_at <= ? ORDER BY updated_at DESC";
        return jdbc.query(sql, planRowMapper, userName, from, to);
    }

    private final RowMapper<Plan> planRowMapper = (rs, rowNum) ->
            new Plan(
                rs.getLong("plan_id"),
                rs.getString("plan_text"),
                rs.getString("user_name"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
            );
}
