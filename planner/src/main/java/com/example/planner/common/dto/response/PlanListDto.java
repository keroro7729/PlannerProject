package com.example.planner.common.dto.response;

import com.example.planner.plan.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlanListDto {
    private List<PlanDto> plans;

    public PlanListDto(List<Plan> list) {
        this.plans = list.stream()
                .map(PlanDto::new)
                .collect(Collectors.toList());
    }
}
