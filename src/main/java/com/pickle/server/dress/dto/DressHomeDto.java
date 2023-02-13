package com.pickle.server.dress.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DressHomeDto {
    List<DressOverviewDto> recentView = new ArrayList<>();
    List<DressOverviewDto> newDresses = new ArrayList<>();
    List<DressOverviewDto> recDresses = new ArrayList<>();

    public DressHomeDto(List<DressOverviewDto> recentView, List<DressOverviewDto> newDresses, List<DressOverviewDto> recDresses) {
        this.recentView = recentView;
        this.newDresses = newDresses;
        this.recDresses = recDresses;
    }
}
