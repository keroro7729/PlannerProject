package com.example.planner.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
}
