package com.training.api.services.impls;

import com.training.api.entitys.TblPost;
import com.training.api.repositorys.PostRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
public class PostServiceTest {
    @InjectMocks
    private PostServiceImpl postService;

    @MockBean
    PostRepository postRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllPost(){
        TblPost tblPost = new TblPost(382013,"9998227",0,0,0);
        List<TblPost> tblPostList = new ArrayList<>();
        tblPostList.add(tblPost);

        Mockito.when(postRepository.findAll()).thenReturn(tblPostList);
        Assert.assertThat(tblPostList, is(postService.findAllPost()));
    }

    @Test
    public void testFindPostById() {
        TblPost tblPost = new TblPost(382013,"9998227",0,0,0);
        Optional<TblPost> acutalPost = Optional.of(tblPost);

        Mockito.when(postRepository.findById(anyInt())).thenReturn(acutalPost);
        Assert.assertThat(acutalPost, is(postService.findPostById(8652)));
    }

    @Test
    public void testSavePost( ) {
        TblPost tblPost = new TblPost(382013,"9998227",0,0,0);

        Mockito.when(postService.savePost(tblPost)).thenReturn(tblPost);
        Assert.assertThat(tblPost, is(postService.savePost(tblPost)));
    }
}
