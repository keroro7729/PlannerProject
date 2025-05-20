package com.example.planner.common.dto.response;

import com.example.planner.plan.Plan;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlanListResponseDto {
    private List<PlanResponseDto> plans;

    public PlanListResponseDto(List<Plan> list) {
        this.plans = list.stream()
                .map(PlanResponseDto::new)
                .collect(Collectors.toList());
    }
}
