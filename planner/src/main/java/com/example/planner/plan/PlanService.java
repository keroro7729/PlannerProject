package com.example.planner.plan;

import com.example.planner.common.dto.request.CreatePlanRequest;
import com.example.planner.common.dto.request.GetPlanListRequest;
import com.example.planner.common.dto.response.PlanDto;
import com.example.planner.common.dto.response.PlanListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository repository;

    public PlanDto create(CreatePlanRequest request) {
        Plan plan = new Plan(
                request.getPlanText(),
                request.getUserName(),
                request.getPassword()
                );
        return new PlanDto(repository.save(plan));
    }

    public PlanDto read(Long planId) {
        Optional<Plan> result = repository.findById(planId);
        if(result.isPresent())
            return new PlanDto(result.get());
        else throw new RuntimeException(); // 예외처리 추가 필요
    }

    public PlanListDto read(GetPlanListRequest request) {
        System.out.println(request);
        List<Plan> result = repository.findAll(
                request.getUserName(),
                request.getFrom(),
                request.getTo()
        );
        return new PlanListDto(result);
    }
}
