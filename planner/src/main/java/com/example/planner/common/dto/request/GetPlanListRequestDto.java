package com.example.planner.common.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter // setter 필요함!! RequestBody와 ModelAttribute의 매핑방식 차이
@ToString
public class GetPlanListRequestDto {
    private String userName;

    // String to LocalDateTime format 지정을 안해줬었음
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime to;
}
