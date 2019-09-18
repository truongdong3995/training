package com.training.api.service.impl;

import com.training.api.entity.TblArea;
import com.training.api.repository.AreaRepository;
import com.training.api.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    /**
     * Get information by postcode
     *
     * @param  postCode postcode;
     *
     * @return List of {@link TblArea}
     */
    @Override
    public List<TblArea> searchAreaByPostCode(String postCode) {
        return areaRepository.findByTblPost_PostCode(postCode);
    }
}
