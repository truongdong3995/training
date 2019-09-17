package com.training.api.repository;

import com.training.api.entity.TblArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<TblArea, Integer> {

    /**
     * Get area by city id
     *
     * @param cityId city id
     *
     * @return List of {@link TblArea}
     */
    List<TblArea> findByTblCity_CityId(int cityId);

    /**
     * Search address by post code
     *
     * @param postCode post code
     *
     * @return List of {@link TblArea}
     */
    List<TblArea> findByTblPost_PostCode(String postCode);

    /**
     * Search record in tbl_area with post id
     *
     * @param postId post_id
     *
     * @return List of {@link TblArea}
     */
    List<TblArea> findByTblPost_PostId(int postId);
}
