package com.training.api.services.impls;

import com.training.api.entitys.TblPost;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import com.training.api.utils.exceptions.ConflicException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class PostServiceTest {
    @InjectMocks
    private PostServiceImpl postService;

    @MockBean
    PostRepository postRepository;

    @MockBean
    AreaRepository areaRepository;

    private TblPost tblPost;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tblPost = new TblPost(262141,"9998237",0,0,0);
    }

    @Test
    public void findAllPost(){
        List<TblPost> tblPostList = new ArrayList<>();
        tblPostList.add(tblPost);

        Mockito.when(postRepository.findAll()).thenReturn(tblPostList);
        Assert.assertThat(tblPostList, is(postService.findAllPost()));
    }

    @Test
    public void findPostById() {
        // setup
        Optional<TblPost> expectedCity = Optional.of(tblPost);
        Mockito.when(postRepository.findById(anyInt())).thenReturn(expectedCity);

        // exercise
        TblPost actualPost = postService.findPostById(1);

        //verify
        Assert.assertThat(actualPost,is(expectedCity.get()));
    }


    @Test(expected = NoExistResourcesException.class)
    public void findPostByIdThrowNERE(){
        // exercise
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.empty());
        postService.findPostById(0);
    }

    @Test
    public void create() {
        // exercise
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.empty());
        Mockito.when(postRepository.save(any(TblPost.class))).thenReturn(tblPost);
        TblPost actual = postService.create(tblPost);

        // verify
        assertThat(actual).isEqualTo(tblPost);
    }

    @Test(expected = ConflicException.class)
    public void createThrowCE() {
        // exercise
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
        postService.create(tblPost);
    }

    @Test
    public void update() {
        // setup
        TblPost actual = new TblPost(382016,"9998237",0,0,0);

        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
        Mockito.when(postRepository.save(any(TblPost.class))).thenReturn(actual);

        // exercise
        TblPost updatePost = postService.update(tblPost.getPostId(), tblPost);

        // verify
        assertThat(actual.getPostCode()).isEqualTo(updatePost.getPostCode());
        assertThat(actual.getMultiArea()).isEqualTo(updatePost.getMultiArea());
        verify(postRepository, times(1)).findById(anyInt());
    }

    @Test
    public void delete() {
        // setup
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));

        // exercise
        TblPost actual = postService.deletePost(tblPost.getPostId());

        //verify
        verify(areaRepository, times(1)).findByTblPost_PostId(anyInt());
        verify(areaRepository, times(1)).deleteAll(anyList());
        assertThat(actual).isEqualTo(tblPost);
    }
}
