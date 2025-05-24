package com.example.planner.database.deletelog;

import com.example.planner.common.base.Repository;
import com.example.planner.database.plan.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.SQLWarningException;
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
public class DeleteLogRepository implements Repository<DeleteLog> {

    private final JdbcTemplate jdbc;

    @Override
    public DeleteLog save(DeleteLog log) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc);
        insert.withTableName("delete_log").usingGeneratedKeyColumns("log_id");

        Map<String, Object> params = new HashMap<>();
        params.put("entity_name", log.getEntityName());
        params.put("entity_id", log.getEntityId());
        params.put("created_at", log.getCreatedAt());
        params.put("updated_at", log.getUpdatedAt());

        Number id = insert.executeAndReturnKey(new MapSqlParameterSource(params));
        log.setLogId(id.longValue());
        return log;
    }

    @Override
    public Optional<DeleteLog> findById(Long id) {
        String sql = "SELECT * FROM delete_log WHERE log_id = ?";
        List<DeleteLog> result = jdbc.query(sql, logRowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<DeleteLog> findAll() {
        String sql = "SELECT * FROM delete_log";
        return jdbc.query(sql, logRowMapper);
    }

    @Override
    public Optional<DeleteLog> update(DeleteLog log) {
        throw new RuntimeException("cant update delete_log");
        //return Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM delete_log WHERE log_id = ?";
        return jdbc.update(sql, id) == 1;
    }

    public Optional<DeleteLog> findByNameId(String name, Long id) {
        String sql = "SELECT * FROM delete_log WHERE entity_name = ? AND entity_id = ?";
        List<DeleteLog> result =  jdbc.query(sql, logRowMapper, name, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    private final RowMapper<DeleteLog> logRowMapper = (rs, rowNum) ->
            new DeleteLog(
                    rs.getLong("log_id"),
                    rs.getString("entity_name"),
                    rs.getLong("entity_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
}
