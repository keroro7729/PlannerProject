package com.example.planner.database.deletelog;

import com.example.planner.common.base.Entity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
public class DeleteLog extends Entity {
    @Setter
    private Long logId;
    private String entityName;
    private Long entityId;

    // copy constructor
    public DeleteLog(Long logId, String entityName, Long entityId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.logId = logId;
        this.entityName = entityName;
        this.entityId = entityId;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }

    // create constructor
    public DeleteLog(String entityName, Long entityId) {
        super();
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
