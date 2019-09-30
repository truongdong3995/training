package com.training.api.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Locale;

/**
 * Class handle message of API
 *
 */
@AllArgsConstructor
@Component
public class ApiMessage {
	private final MessageSource messageSource;

	public String getMessageError(String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}
}
