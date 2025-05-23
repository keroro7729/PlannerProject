package com.example.planner.database.plan;

import com.example.planner.common.base.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        insert.withTableName("plans").usingGeneratedKeyColumns("plan_id");

        Map<String, Object> params = new HashMap<>();
        params.put("plan_text", plan.getPlanText());
        params.put("anonymity", plan.getAnonymity());
        params.put("created_at", plan.getCreatedAt());
        params.put("updated_at", plan.getUpdatedAt());
        params.put("user_id", plan.getUserId());

        // 여기서 에러!! 리턴 Number to Long에서 오류
        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        plan.setPlanId(id.longValue());
        return plan;
    }

    @Override
    public Optional<Plan> findById(Long id) {
        String sql = "SELECT * FROM plans WHERE plan_id = ?";
        List<Plan> result = jdbc.query(sql, planRowMapper, id);
        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public List<Plan> findAll() {
        String sql = "SELECT * FROM plans";
        return jdbc.query(sql, planRowMapper);
    }

    @Override
    public Optional<Plan> update(Plan plan) {
        String sql = "UPDATE plans SET plan_text = ?, anonymity = ?, updated_at = ? WHERE plan_id = ?";
        int updated = jdbc.update(sql, plan.getPlanText(), plan.getAnonymity(), plan.getUpdatedAt(), plan.getPlanId());
        return updated == 1 ? Optional.of(plan) : Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM plans WHERE plan_id = ?";
        return jdbc.update(sql, id) == 1;
    }

    // custom query

    public List<Plan> findAllByAnonymityFalse(int limit, int offset) {
        String sql = "SELECT * FROM plans WHERE anonymity = false ORDER BY updated_at LIMIT ? OFFSET ?";
        return jdbc.query(sql, planRowMapper, limit, offset);
    }

    public long countByAnonymityFalse() {
        String sql = "SELECT count(*) FROM plans WHERE anonymity = false";
        Integer result = jdbc.queryForObject(sql, Integer.class);
        return result == null ? 0 : result.longValue();
    }

    public List<Plan> findAllByUserId(Long userId, int limit, int offset) {
        String sql = "SELECT * FROM plans WHERE user_id = ? ORDER BY updated_at LIMIT ? OFFSET ?";
        return jdbc.query(sql, planRowMapper, userId, limit, offset);
    }

    public long countByUserId(Long userId) {
        String sql = "SELECT count(*) FROM plans WHERE user_id = ?";
        Integer result = jdbc.queryForObject(sql, Integer.class, userId);
        return result == null ? 0 : result.longValue();
    }

    private final RowMapper<Plan> planRowMapper = (rs, rowNum) ->
            new Plan(
                rs.getLong("plan_id"),
                rs.getString("plan_text"),
                rs.getBoolean("anonymity"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getLong("user_id")
            );
}
