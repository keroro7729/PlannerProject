package com.example.planner.plan;

import com.example.planner.common.dto.request.CreatePlanRequest;
import com.example.planner.common.dto.request.GetPlanListRequest;
import com.example.planner.common.dto.response.PlanDto;
import com.example.planner.common.dto.response.PlanListDto;
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
    public ResponseEntity<PlanDto> create(@RequestBody CreatePlanRequest request) {
        PlanDto created = service.create(request);
        URI location = URI.create("/api/plans/"+created.getPlanId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<PlanListDto> read(@ModelAttribute GetPlanListRequest request) {
        return ResponseEntity.ok(service.read(request));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanDto> read(@PathVariable Long planId) {
        return ResponseEntity.ok(service.read(planId));
    }
}
