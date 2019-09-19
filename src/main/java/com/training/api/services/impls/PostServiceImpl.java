package com.training.api.services.impls;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblPost;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import com.training.api.services.PostService;
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
