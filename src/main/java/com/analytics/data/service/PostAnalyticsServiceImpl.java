package com.analytics.data.service;

import com.analytics.data.dto.CarPostDto;
import com.analytics.data.entity.BrandAnalyticsEntity;
import com.analytics.data.entity.CarModelAnalyticsEntity;
import com.analytics.data.entity.CarModelPriceEntity;
import com.analytics.data.repository.BrandAnalyticsRepository;
import com.analytics.data.repository.CarModelAnalyticsRepository;
import com.analytics.data.repository.CarModelPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostAnalyticsServiceImpl implements PostAnalyticsService {

    @Autowired
    BrandAnalyticsRepository brandAnalyticsRepository;

    @Autowired
    CarModelPriceRepository carModelPriceRepository;

    @Autowired
    CarModelAnalyticsRepository carModelAnalyticsRepository;


    @Override
    public void saveDataAnalytics(CarPostDto carPostDto) {
        saveBrandAnalytics(carPostDto.getBrand());
        saveCarModelAnalytics(carPostDto.getModel());
        saveCarModelPriceAnalytics(carPostDto.getModel(), carPostDto.getPrice());

    }

    private void saveCarModelPriceAnalytics(String model, Double price) {
        CarModelPriceEntity carModelPriceEntity = new CarModelPriceEntity();

        carModelPriceEntity.setModel(model);
        carModelPriceEntity.setPrice(price);
        carModelPriceRepository.save(carModelPriceEntity);

    }

    private void saveCarModelAnalytics(String model) {
        CarModelAnalyticsEntity carModelAnalyticsEntity = new CarModelAnalyticsEntity();

        carModelAnalyticsRepository.findByModel(model).ifPresentOrElse(item->{
            item.setPosts(item.getPosts()+1);
            carModelAnalyticsRepository.save(item);
        }, ()-> {
            carModelAnalyticsEntity.setModel(model);
            carModelAnalyticsEntity.setPosts(1L);
            carModelAnalyticsRepository.save(carModelAnalyticsEntity);
        });

    }

    private void saveBrandAnalytics(String brand) {
        BrandAnalyticsEntity brandAnalyticsEntity = new BrandAnalyticsEntity();

        brandAnalyticsRepository.findByBrand(brand).ifPresentOrElse(item -> {
            item.setPosts(item.getPosts() + 1);
            brandAnalyticsRepository.save(item);
        }, () -> {
            brandAnalyticsEntity.setBrand(brand);
            brandAnalyticsEntity.setPosts(1L);
            brandAnalyticsRepository.save(brandAnalyticsEntity);
        });


    }
}
