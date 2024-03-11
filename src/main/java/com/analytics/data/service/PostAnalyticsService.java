package com.analytics.data.service;

import com.analytics.data.dto.CarPostDto;
import org.springframework.stereotype.Service;

@Service
public interface PostAnalyticsService {

    void saveDataAnalytics(CarPostDto carPostDto);


}
