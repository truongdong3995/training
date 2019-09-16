package com.training.api.repository;

import com.training.api.entity.TblCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<TblCity, Integer>, CityRepositoryCustom {
}
