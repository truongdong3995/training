package com.training.api.controllers;

import com.training.api.models.HttpExceptionResponse;
import com.training.api.models.SearchPostCodeResponse;
import com.training.api.models.SearchPrefectureCodeResponse;
import com.training.api.services.CityService;
import com.training.api.services.PostOfficeService;
import com.training.api.services.PostService;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@Transactional
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/post_offices")
public class PostOfficeController {
	
	private final PostOfficeService postOfficeService;
	
	
	/**
	 * Request processing when call url mapping search address by prefecture code
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return List of {@link SearchPrefectureCodeResponse} found
	 */
	@RequestMapping(value = "/prefectures/{prefectureCode}", method = RequestMethod.GET)
	public ResponseEntity searchAddressByPrefectureCode(@PathVariable("prefectureCode") String prefectureCode) {
		try {
			List<SearchPrefectureCodeResponse> prefectureCodeResponseList =
					postOfficeService.searchAddressByPrefectureCode(prefectureCode);
			
			return new ResponseEntity<>(new RestData(prefectureCodeResponseList), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Function request processing when call url mapping
	 *
	 * @param postCode post code
	 *
	 * @return List of {@link SearchPostCodeResponse} found
	 */
	@RequestMapping(value = "/post/{postCode}", method = RequestMethod.GET)
	public ResponseEntity searchAddressByPostCode(@PathVariable("postCode") String postCode) {
		try {
			List<SearchPostCodeResponse> postCodeResponseList = postOfficeService.searchAddressByPostCode(postCode);
			
			return new ResponseEntity<>(new RestData(postCodeResponseList), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new HttpExceptionResponse("404", e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
}
