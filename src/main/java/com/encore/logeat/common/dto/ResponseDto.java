package com.encore.logeat.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseDto {
	private HttpStatus httpStatus;
	private String message;
	private Object result;

}