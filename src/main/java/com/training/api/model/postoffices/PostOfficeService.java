package com.training.api.model.postoffices;

import com.amazonaws.util.CollectionUtils;
import com.training.api.model.cities.CityRepository;
import com.training.api.web.postoffices.SearchPostCodeResponse;
import com.training.api.web.postoffices.SearchPrefectureCodeResponse;
import com.training.api.model.areas.AreaRepository;
import com.training.api.utils.ApiMessage;
import com.training.api.utils.Common;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service search.
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostOfficeService {
	
	private final AreaRepository areaRepository;
	
	private final ApiMessage apiMessage;
	
	private final CityRepository cityRepository;
	
	
	/**
	 * Get address information by prefecture code.
	 *
	 * @param prefectureCode prefecture code
	 *
	 * @return searchPrefectureCodeResponseList List of {@link SearchPrefectureCodeResponse}
	 * @throws IllegalArgumentException if input invalid
	 * @throws NotFoundException if not found
	 */
	public List<SearchPrefectureCodeResponse> searchByPrefectureCode(String prefectureCode)
			throws NotFoundException {
		if (Common.checkValidNumber(prefectureCode) == false) {
			throw new IllegalArgumentException(
					apiMessage.getMessageError("service.city.search.prefecture_code_invalid", prefectureCode));
		}
		
		List<SearchPrefectureCodeResponse> searchPrefectureCodeResponseList =
				cityRepository.findByPrefecturePrefectureCode(prefectureCode)
					.stream().map(SearchPrefectureCodeResponse::new).collect(Collectors.toList());
		if (CollectionUtils.isNullOrEmpty(searchPrefectureCodeResponseList)) {
			throw new NotFoundException(
					apiMessage.getMessageError("service.city.search.prefecture_code_not_found", prefectureCode));
		}
		
		return searchPrefectureCodeResponseList;
	}
	
	/**
	 * Get address information by post code.
	 *
	 * @param  postCode postcode;
	 *
	 * @return searchPostCodeResponseList List of {@link SearchPostCodeResponse}
	 */
	public List<SearchPostCodeResponse> searchByPostCode(String postCode) throws NotFoundException {
		if (Common.checkValidNumber(postCode) == false) {
			throw new IllegalArgumentException(
					apiMessage.getMessageError("service.city.search.post_code_invalid", postCode));
		}
		
		List<SearchPostCodeResponse> searchPostCodeResponseList =
				areaRepository.findByPostPostCode(postCode).stream()
					.map(SearchPostCodeResponse::new).collect(Collectors.toList());
		
		if (CollectionUtils.isNullOrEmpty(searchPostCodeResponseList)) {
			throw new NotFoundException(
					apiMessage.getMessageError("service.city.search.post_code_not_found", postCode));
		}
		return searchPostCodeResponseList;
	}
}
