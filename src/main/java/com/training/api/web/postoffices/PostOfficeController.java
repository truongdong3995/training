package com.training.api.web.postoffices;

import com.training.api.model.postoffices.PostOfficeService;
import com.training.api.utils.RestData;
import javassist.NotFoundException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpBadRequestException;
import jp.xet.sparwings.spring.web.httpexceptions.HttpNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Spring MVC RESTful Controller to search address.
 *
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/post_offices")
public class PostOfficeController {
	
	private final PostOfficeService postOfficeService;
	
	
	/**
	 * Search address by prefecture code.
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return List of {@link SearchPrefectureCodeResponse} found
	 */
	@RequestMapping(value = "/prefectures/{prefectureCode}", method = RequestMethod.GET)
	public ResponseEntity searchAddressByPrefectureCode(
			@PathVariable("prefectureCode") String prefectureCode) {
		try {
			List<SearchPrefectureCodeResponse> prefectureCodeResponseList =
					postOfficeService.searchByPrefectureCode(prefectureCode);
			RestData<List> resource = new RestData<>(prefectureCodeResponseList);

			return ResponseEntity.ok(resource);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
	
	/**
	 * Search address by post code.
	 *
	 * @param postCode post code
	 *
	 * @return List of {@link SearchPostCodeResponse} found
	 */
	@RequestMapping(value = "/posts/{postCode}", method = RequestMethod.GET)
	public ResponseEntity searchAddressByPostCode(
			@PathVariable("postCode") String postCode) {
		try {
			List<SearchPostCodeResponse> postCodeResponseList = postOfficeService.searchByPostCode(postCode);
			RestData<List> resource = new RestData<>(postCodeResponseList);

			return ResponseEntity.ok(resource);
		} catch (IllegalArgumentException e) {
			throw new HttpBadRequestException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new HttpNotFoundException(e.getMessage(), e);
		}
	}
}
