package com.training.api.controllers;

import com.training.api.utils.exceptions.InvalidInputException;
import com.training.api.utils.exceptions.NoExistResourcesException;
import com.training.api.models.PostCodeResponse;
import com.training.api.services.AreaService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * Function request processing when call url mapping
     *
     * @param postcode post code
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/post_offices/post/{postcode}", method = RequestMethod.GET)
    public ResponseEntity searchByPostCode(@PathVariable("postcode") String postcode) {
        try {
            List<PostCodeResponse> postCodeResponseList = areaService.searchAreaByPostCode(postcode).stream()
                    .map(PostCodeResponse::new).collect(Collectors.toList());
            return new ResponseEntity<>(new RestData(postCodeResponseList), HttpStatus.OK);
        } catch (InvalidInputException ex) {
            return new ResponseEntity<>(ApiMessage.error400(), HttpStatus.BAD_REQUEST);
        } catch (NoExistResourcesException ex) {
            return new ResponseEntity<>(ApiMessage.error404(), HttpStatus.NOT_FOUND);
        }
    }
}
