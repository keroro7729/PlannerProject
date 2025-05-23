package com.example.planner.controller;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.DeletePlanRequestDto;
import com.example.planner.common.dto.request.GetPlanListRequestDto;
import com.example.planner.common.dto.request.UpdatePlanRequestDto;
import com.example.planner.common.dto.response.PlanResponseDto;
import com.example.planner.common.dto.response.PlanListResponseDto;
import com.example.planner.database.plan.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService service;

    @PostMapping
    public ResponseEntity<PlanResponseDto> create(@RequestBody CreatePlanRequestDto body,
                                                  HttpServletRequest request) {
        PlanResponseDto created = service.createPlan(body, request);
        URI location = URI.create("/api/plans/"+created.getPlanId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<PlanListResponseDto> viewAllPlans() {
        return ResponseEntity.ok(service.viewAllPlans());
    }

    @GetMapping("/me")
    public ResponseEntity<PlanListResponseDto> view(HttpServletRequest request) {
        return ResponseEntity.ok(service.viewUsersPlans(request));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> read(@PathVariable Long planId,
                                                HttpServletRequest request) {
        return ResponseEntity.ok(service.viewPlan(planId, request));
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> update(@PathVariable Long planId,
                                                  @RequestBody UpdatePlanRequestDto body,
                                                  HttpServletRequest request) {
        return ResponseEntity.ok(service.updatePlan(planId, body, request));
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> delete(@PathVariable Long planId,
                                       @RequestBody DeletePlanRequestDto body,
                                       HttpServletRequest request) {
        service.deletePlan(planId, body, request);
        return ResponseEntity.noContent().build();
    }
}
