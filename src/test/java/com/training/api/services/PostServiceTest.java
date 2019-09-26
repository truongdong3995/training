package com.training.api.services;

import com.training.api.entitys.TblArea;
import com.training.api.entitys.TblPost;
import com.training.api.entitys.fixtures.TblAreaFixtures;
import com.training.api.entitys.fixtures.TblPostFixtures;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.UpdatePostRequest;
import com.training.api.models.fixtures.SearchPostCodeResponseFixtures;
import com.training.api.models.fixtures.UpdateCityRequestFixtures;
import com.training.api.models.fixtures.UpdatePostRequestFixtures;
import com.training.api.repositorys.AreaRepository;
import com.training.api.repositorys.PostRepository;
import com.training.api.utils.exceptions.ConflicException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for {@link PostService}.
 */
@RunWith(SpringRunner.class)
public class PostServiceTest {
    @InjectMocks
    private PostService sut;

    @MockBean
    PostRepository postRepository;

    @MockBean
    AreaRepository areaRepository;

    private TblPost tblPost;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test find all post
     *
     */
    @Test
    public void findAllPost(){
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        List<TblPost> tblPostList = new ArrayList<>();
        tblPostList.add(tblPost);
        Mockito.when(postRepository.findAll()).thenReturn(tblPostList);
        // exercise
        List<TblPost> actual = sut.findAllPost();
        // verify
        verify(postRepository, times(1)).findAll(   );
        assertThat(actual).isEqualTo(tblPostList);
    }

    /**
     * Test search address by post code
     *
     */
    @Test
    public void searchAddressByPostCode() throws NotFoundException {
        // setup
        TblArea tblArea = TblAreaFixtures.createArea();
        SearchPostCodeResponse searchPostCodeResponse =
                SearchPostCodeResponseFixtures.createReponse(tblArea);
        List<TblArea> tblAreaList = new ArrayList<>();
        tblAreaList.add(tblArea);
        Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(tblAreaList);
        // exercise
        List<SearchPostCodeResponse> actual =
                sut.searchAddressByPostCode(tblArea.getTblPost().getPostCode());
        // verify
        assertThat(actual.get(0)).isEqualTo(searchPostCodeResponse);
    }

    /**
     * Test search address by post code throws IllegalArgumentException
     *
     */
    @Test
    public void searchSearchAddressByPostCodeThrowIAE(){
        // exercise
        assertThatThrownBy(() -> sut.searchAddressByPostCode(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test search address by post code throws NotFoundException
     *
     */
    @Test
    public void searchSearchAddressByPostCodeThrowNFE(){
        Mockito.when(areaRepository.findByTblPost_PostCode(anyString())).thenReturn(new ArrayList<>());
        // exercise
        assertThatThrownBy(() -> sut.searchAddressByPostCode(anyString()))
                .isInstanceOf(NotFoundException.class);
    }

    /**
     * Test find Post by id
     *
     */
    @Test
    public void findPostById() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));

        // exercise
        Optional<TblPost> actual = sut.findPostById(String.valueOf(tblPost.getPostId()));

        //verify
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(tblPost);
        verify(postRepository, times(1)).findById(tblPost.getPostId());
    }

    /**
     * Test find Post by id throws IllegalArgumentException
     *
     */
    @Test
    public void findPostByIdThrowIAE(){
        // exercise
        assertThatThrownBy(() -> sut.findPostById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test create new Post
     *
     */
    @Test
    public void create() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        when(postRepository.save(any(TblPost.class))).thenReturn(tblPost);

        // exercise
        TblPost actual = sut.create(tblPost);
        // verify
        assertThat(actual).isEqualTo(tblPost);
    }

    /**
     * Test create new Post throws NullPointerException.
     *
     */
    @Test
    public void createThrowsNPE() {
        // exercise
        assertThatThrownBy(() -> sut.create(null)).isInstanceOf(NullPointerException.class);
    }

    /**
     * Test create new Post throws ConflicException.
     *
     */
    @Test
    public void createThrowsCE() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(TblPost.class));
        // exercise
        assertThatThrownBy(() -> sut.create(null)).isInstanceOf(ConflicException.class);
    }

    /**
     * Test update Post if exist.
     *
     */
    @Test
    public void update() throws NotFoundException {
        // setup
        UpdatePostRequest request = UpdatePostRequestFixtures.createRequest();
        TblPost tblPost = TblPostFixtures.createPost();
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
        Mockito.when(postRepository.save(any(TblPost.class))).thenReturn(tblPost);
        // exercise
        TblPost actual = sut.update(String.valueOf(tblPost.getPostId()), request);
        // verify
        assertThat(actual).isEqualTo(tblPost);
    }

    /**
     * Test update Post if exist throws NullPointerException.
     *
     */
    @Test
    public void updateThrowsNPE() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        // exercise
        assertThatThrownBy(() -> sut.update(String.valueOf(tblPost.getPostId()), null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Test update Post if exist throws IllegalArgumentException.
     *
     */
    @Test
    public void updateThrowsIAE() {
        // setup
        UpdatePostRequest request = UpdatePostRequestFixtures.createRequest();
        // exercise
        assertThatThrownBy(() -> sut.update(null, request)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test update Post if exist throws ConflicException.
     *
     */
    @Test
    public void updateThrowsCE() {
        // setup
        UpdatePostRequest request = UpdatePostRequestFixtures.createRequest();
        TblPost tblPost = TblPostFixtures.createPost();
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
        doThrow(DataIntegrityViolationException.class).when(postRepository).save(any(TblPost.class));
        // exercise
        assertThatThrownBy(() -> sut.update("111", request))
                .isInstanceOf(ConflicException.class);
    }

    /**
     * Test delete Post if exist.
     *
     */
    @Test
    public void deletePost() throws NotFoundException {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        Mockito.when(postRepository.findById(anyInt())).thenReturn(Optional.of(tblPost));
        Mockito.when(areaRepository.findByTblCity_CityId(anyInt())).thenReturn(new ArrayList<>());
        // exercise
        TblPost actual = sut.deletePost(String.valueOf(tblPost.getPostId()));
        // verify
        verify(postRepository, times(1)).delete(tblPost);
        assertThat(actual).isEqualTo(tblPost);
    }

    /**
     * Test delete Post if exist throws IllegalArgumentException.
     *
     */
    @Test
    public void deletePostThrowsIAE() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        // exercise
        assertThatThrownBy(() -> sut.deletePost(String.valueOf(tblPost.getPostId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test delete Post if exist throws NotFoundException.
     *
     */
    @Test
    public void deletePostThrowsNFE() {
        // setup
        TblPost tblPost = TblPostFixtures.createPost();
        doThrow(NotFoundException.class).when(postRepository).findById(anyInt());
        // exercise
        assertThatThrownBy(() -> sut.deletePost(String.valueOf(tblPost.getPostId())))
                .isInstanceOf(NotFoundException.class);
    }
}
