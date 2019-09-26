package com.training.api.repositorys;

import com.training.api.entitys.TblCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<TblCity, Integer>{
    /**
     * Get list City by prefecture code
     *
     * @param prefectureCode prefecture code
     *
     * @return list of {@link TblCity}
     */
    List<TblCity> findByTblPrefecture_PrefectureCode(String prefectureCode);
}
