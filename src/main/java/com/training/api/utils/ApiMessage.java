package com.training.api.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


/**
 * Class handle message of API
 *
 */
@AllArgsConstructor
@Component
public class ApiMessage {
	private final MessageSource messageSource;

	/**
	 * Get message from message properties
	 *
	 * @param code message code
	 *
	 * @return message
	 */
	public String getMessageError(String code, Object... args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
