package com.example.planner.database.plan;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.DeletePlanRequestDto;
import com.example.planner.common.dto.request.UpdatePlanRequestDto;
import com.example.planner.common.dto.response.PageResponseDto;
import com.example.planner.common.dto.response.PlanResponseDto;
import com.example.planner.database.user.UserService;
import com.example.planner.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository repository;
    private final UserService userService;

    public PlanResponseDto createPlan(CreatePlanRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        userService.checkPassword(userId, body.getPassword());
        Plan plan = new Plan(body.getPlanText(), body.getAnonymity(), userId);
        plan = repository.save(plan);
        return new PlanResponseDto(plan);
    }

    public PageResponseDto<PlanResponseDto> viewAllPlans(int page, int size) {
        int limit = size, offset = page * size;
        List<PlanResponseDto> items =
                repository.findAllByAnonymityFalse(limit, offset)
                .stream()
                .map(PlanResponseDto::new)
                .toList();
        return new PageResponseDto<PlanResponseDto>(items, page, size, repository.countByAnonymityFalse());
    }

    public PageResponseDto<PlanResponseDto> viewUsersPlans(int page, int size, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        int limit = size, offset = page * size;
        List<PlanResponseDto> items =
                repository.findAllByUserId(userId, limit, offset)
                        .stream()
                        .map(PlanResponseDto::new)
                        .toList();
        return new PageResponseDto<PlanResponseDto>(items, page, size, repository.countByUserId(userId));
    }

    public PlanResponseDto viewPlan(Long planId, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("plan not found: "+planId));
        if(!plan.getUserId().equals(userId))
            throw new RuntimeException("access denied: planId="+planId+", requested-user="+userId);
        return new PlanResponseDto(plan);
    }

    public PlanResponseDto updatePlan(Long planId, UpdatePlanRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("plan not found: "+planId));
        if(!plan.getUserId().equals(userId))
            throw new RuntimeException("access denied: planId="+planId+", requested-user="+userId);
        userService.checkPassword(plan.getUserId(), body.getPassword());

        String planText = body.getPlanText() != null ? body.getPlanText() : plan.getPlanText();
        Boolean anonymity = body.getAnonymity() != null ? body.getAnonymity() : plan.getAnonymity();
        plan.update(planText, anonymity);
        repository.update(plan);
        return new PlanResponseDto(plan);
    }

    public void deletePlan(Long planId, DeletePlanRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new RuntimeException("plan not found: "+planId));
        if(!plan.getUserId().equals(userId))
            throw new RuntimeException("access denied: planId="+planId+", requested-user="+userId);
        userService.checkPassword(plan.getUserId(), body.getPassword());
        repository.delete(planId);
    }
}
