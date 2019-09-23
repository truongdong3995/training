package com.training.api.services.impls;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblPost;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.NoExistResourcesException;
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

    /**
     * Find post by post id
     *
     * @param postId post id
     *
     * @return TblPost {@link TblPost}
     */
    @Override
    public TblPost findPostById(int postId) {
        Optional<TblPost> tblPost = postRepository.findById(postId);
        if (tblPost.isPresent() == false) {
            throw new NoExistResourcesException();
        }

        return tblPost.get();
    }

    /**
     * Create new post
     *
     * @param tblPost object {@link TblPost}
     *
     * @return TblPost {@link TblPost}
     */
    @Override
    public TblPost create(TblPost tblPost) {
        if (postRepository.findById(tblPost.getPostId()).isPresent()){
            throw  new ConflicException();
        }
        TblPost createPost = postRepository.save(tblPost);

        return createPost;
    }

    /**
     * Delete post by post id
     *
     * @param postId post id
     *
     * @return TblPost {@link TblPost}
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public TblPost deletePost(int postId) {
        TblPost tblPost = findPostById(postId);

        List<TblArea> tblAreaList= areaRepository.findByTblPost_PostId(postId);
        areaRepository.deleteAll(tblAreaList);
        postRepository.delete(tblPost);

        return tblPost;
    }

    /**
     * Update post
     *
     * @param postId post id
     * @param request {@link TblPost}
     *
     * @return TblPost {@link TblPost}
     */
    @Override
    public TblPost update(int postId, TblPost request) {
        TblPost tblPost = findPostById(postId);
        tblPost.setPostCode(request.getPostCode());
        tblPost.setMultiArea(request.getMultiArea());
        TblPost updatePost = postRepository.save(tblPost);

        return updatePost;
    }
}
