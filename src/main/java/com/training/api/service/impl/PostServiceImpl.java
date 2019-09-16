package com.training.api.service.impl;

import com.training.api.entity.TblArea;
import com.training.api.repository.PostRepository;
import com.training.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    /**
     * Get information by postcode
     *
     * @param  postCode postcode;
     *
     * @return TblArea
     */
    @Override
    public List<TblArea> searchAreaByPostCode(String postCode) {
        return postRepository.searchAreaByPostCode(postCode);
    }

    /**
     * Search address by post code 2
     *
     * @param postCode post code
     * @return List of {@link TblArea}
     */
    @Override
    public List<TblArea> findByTblPostPostcode(String postCode) {
        return postRepository.findByTblPost_PostCode(postCode);
    }
}
