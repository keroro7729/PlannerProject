package com.example.planner.plan;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.GetPlanListRequestDto;
import com.example.planner.common.dto.response.PlanResponseDto;
import com.example.planner.common.dto.response.PlanListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository repository;

    public PlanResponseDto create(CreatePlanRequestDto request) {
        Plan plan = new Plan(
                request.getPlanText(),
                request.getUserName(),
                request.getPassword()
                );
        return new PlanResponseDto(repository.save(plan));
    }

    public PlanResponseDto read(Long planId) {
        Optional<Plan> result = repository.findById(planId);
        if(result.isPresent())
            return new PlanResponseDto(result.get());
        else throw new RuntimeException(); // 예외처리 추가 필요
    }

    public PlanListResponseDto read(GetPlanListRequestDto request) {
        System.out.println(request);
        List<Plan> result = repository.findAll(
                request.getUserName(),
                request.getFrom(),
                request.getTo()
        );
        return new PlanListResponseDto(result);
    }
}
