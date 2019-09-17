package com.training.api.service.impl;

import com.training.api.entity.TblArea;
import com.training.api.entity.TblPost;
import com.training.api.repository.AreaRepository;
import com.training.api.repository.PostRepository;
import com.training.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<TblPost> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Optional<TblPost> findPostById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public TblPost savePost(TblPost tblPost) {
        return postRepository.save(tblPost);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deletePost(TblPost tblPost) {
        List<TblArea> tblAreaList= areaRepository.findByTblPost_PostId(tblPost.getPostId());
        areaRepository.deleteAll(tblAreaList);
        postRepository.delete(tblPost);
    }
}
