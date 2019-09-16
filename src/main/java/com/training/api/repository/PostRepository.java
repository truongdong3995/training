package com.training.api.repository;

import com.training.api.entity.TblArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<TblArea, Integer>, PostRepositoryCustom {

    /**
     * Search address by post code 2
     *
     * @param postCode post code
     * @return List of {@link TblArea}
     */
    List<TblArea> findByTblPost_PostCode(String postCode);
}
