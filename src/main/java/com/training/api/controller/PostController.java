package com.training.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.training.api.entity.TblArea;
import com.training.api.entity.TblCity;
import com.training.api.model.PostCodeResponse;
import com.training.api.service.PostService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import com.training.api.utils.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Function request processing when call url mapping
     *
     * @param postcode post code
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/post/{postcode}", method = RequestMethod.GET)
    public ResponseEntity searchByPostCode(@PathVariable("postcode") String postcode) {
        if (Common.checkValidNumber(Common.replaceData(postcode)) == false) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }

        List<TblArea> areaList = postService.searchAreaByPostCode(postcode);
        if (areaList.size() == 0) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new RestData(getListResponseFromArea(areaList)), HttpStatus.OK);
    }

    @RequestMapping(value = "/post_offices/post/v2/{postcode}")
    public ResponseEntity searchByPostcode2(@PathVariable("postcode") String postcode){
        if (Common.checkValidNumber(Common.replaceData(postcode)) == false) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        }

        List<TblArea> areaList = postService.findByTblPostPostcode(postcode);
        if (areaList.size() == 0) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(new RestData(getListResponseFromArea(areaList)), HttpStatus.OK);
    }

    /**
     * Get list response from area
     *
     * @param areaList List of {@link TblArea}
     *
     * @return List of{@link PostCodeResponse}
     */
    private List<PostCodeResponse> getListResponseFromArea(List<TblArea> areaList) {
        List<PostCodeResponse> searchByPostCodeResponseList = areaList.stream().map(tblArea->
                new PostCodeResponse(tblArea)).collect(Collectors.toList());

        return searchByPostCodeResponseList;
    }
}
