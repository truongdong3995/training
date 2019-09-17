package com.training.api.repository;

import com.training.api.entity.TblCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<TblCity, Integer>{
    /**
     * Search address by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return list of {@link TblCity}
     */
    List<TblCity> findByTblPrefecture_PrefectureCode(String prefectureCode);
}
