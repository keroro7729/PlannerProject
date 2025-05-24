package com.example.planner.database.plan;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.DeletePlanRequestDto;
import com.example.planner.common.dto.request.UpdatePlanRequestDto;
import com.example.planner.common.dto.response.PageResponseDto;
import com.example.planner.common.dto.response.PlanResponseDto;
import com.example.planner.common.exceptions.AccessDeniedException;
import com.example.planner.common.exceptions.ResourceNotFoundException;
import com.example.planner.database.user.UserService;
import com.example.planner.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        PlanResponseDto response = new PlanResponseDto(plan);
        response.setUserName(userService.getUserName(userId));
        return response;
    }

    public PageResponseDto<PlanResponseDto> viewAllPlans(int page, int size) {
        int limit = size, offset = page * size;
        List<PlanResponseDto> items = new ArrayList<>();
        for(Plan plan : repository.findAllByAnonymityFalse(limit, offset)) {
            items.add(new PlanResponseDto(plan));
            items.get(items.size() - 1)
                    .setUserName(userService.getUserName(plan.getUserId()));
        }
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
        items.forEach(o -> o.setUserName(userService.getUserName(userId)));
        return new PageResponseDto<PlanResponseDto>(items, page, size, repository.countByUserId(userId));
    }

    public PlanResponseDto viewPlan(Long planId, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("plan", planId));
        if(!plan.getUserId().equals(userId))
            throw new AccessDeniedException("plan", planId, userId);
        PlanResponseDto response = new PlanResponseDto(plan);
        response.setUserName(userService.getUserName(userId));
        return response;
    }

    public PlanResponseDto updatePlan(Long planId, UpdatePlanRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("plan", planId));
        if(!plan.getUserId().equals(userId))
            throw new AccessDeniedException("plan", planId, userId);
        userService.checkPassword(plan.getUserId(), body.getPassword());

        String planText = body.getPlanText() != null ? body.getPlanText() : plan.getPlanText();
        Boolean anonymity = body.getAnonymity() != null ? body.getAnonymity() : plan.getAnonymity();
        plan.update(planText, anonymity);
        repository.update(plan);
        PlanResponseDto response = new PlanResponseDto(plan);
        response.setUserName(userService.getUserName(userId));
        return response;
    }

    public void deletePlan(Long planId, DeletePlanRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        Plan plan = repository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("plan", planId));
        if(!plan.getUserId().equals(userId))
            throw new AccessDeniedException("plan", planId, userId);
        userService.checkPassword(plan.getUserId(), body.getPassword());
        repository.delete(planId);
    }
}
