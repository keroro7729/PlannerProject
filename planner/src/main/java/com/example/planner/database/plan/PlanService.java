package com.example.planner.database.plan;

import com.example.planner.common.dto.request.CreatePlanRequestDto;
import com.example.planner.common.dto.request.DeletePlanRequestDto;
import com.example.planner.common.dto.request.GetPlanListRequestDto;
import com.example.planner.common.dto.request.UpdatePlanRequestDto;
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


}
