package com.example.planner.plan;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.GetPlanListRequestDto;
import com.example.planner.common.dto.response.PlanResponseDto;
import com.example.planner.common.dto.response.PlanListResponseDto;
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
    public ResponseEntity<PlanResponseDto> create(@RequestBody CreatePlanRequestDto request) {
        PlanResponseDto created = service.create(request);
        URI location = URI.create("/api/plans/"+created.getPlanId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<PlanListResponseDto> read(@ModelAttribute GetPlanListRequestDto request) {
        return ResponseEntity.ok(service.read(request));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponseDto> read(@PathVariable Long planId) {
        return ResponseEntity.ok(service.read(planId));
    }
}
