package com.egen.northwind.api_response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseEntityFactory {
	
	public <T> ResponseEntity<ApiResponse<T>> saveResponse(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.SAVE_SUCCESS.toString(), HttpStatus.CREATED.value(),
			                  Boolean.TRUE, message),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> saveResponse(String message, T t) {
		return new ResponseEntity<>(
			new ApiResponse<T>(t, ApiResponseCode.SAVE_SUCCESS.toString(), HttpStatus.CREATED.value(),
			                   Boolean.TRUE, message),
			new HttpHeaders(), HttpStatus.OK);
	}

	public <T> ResponseEntity<ApiResponse<T>> updateResponse(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.UPDATE_SUCCESS.toString(), HttpStatus.OK.value(),
			                   Boolean.TRUE, message),
			new HttpHeaders(), HttpStatus.OK);
	}

	public <T> ResponseEntity<ApiResponse<T>> updateResponse(String message, T t) {
		return new ResponseEntity<>(
			new ApiResponse<T>(t, ApiResponseCode.UPDATE_SUCCESS.toString(), HttpStatus.OK.value(),
			                   Boolean.TRUE, message),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> deleteResponse() {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.DELETE_SUCCESS.toString(), HttpStatus.OK.value(),
			                   Boolean.TRUE, "Deleted successfully"),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> deleteResponse(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.DELETE_SUCCESS.toString(), HttpStatus.OK.value(),
			                   Boolean.TRUE, message),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T>  ResponseEntity<ApiResponse<T>> getResponse(T t) {
		return new ResponseEntity<>(
			new ApiResponse<T>(t, ApiResponseCode.FETCH_DATA_SUCCESS.toString(), HttpStatus.OK.value(),
			                   Boolean.TRUE, "Fetched data successfully"),
			new HttpHeaders(), HttpStatus.OK);
	}

	public <T>  ResponseEntity<ApiResponse<T>> getResponse(T t, String message) {
		return new ResponseEntity<>(
				new ApiResponse<T>(t, ApiResponseCode.FETCH_DATA_SUCCESS.toString(), HttpStatus.OK.value(),
						Boolean.TRUE, message),
				new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> errorResponse() {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
			                   Boolean.FALSE, "Some unexpected error happened"),
			new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> errorResponse(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
			                   Boolean.FALSE, message),
			new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> notFound() {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.NOT_FOUND.toString(), HttpStatus.OK.value(),
			                   Boolean.FALSE, "Resource not found"),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.NOT_FOUND.toString(), HttpStatus.OK.value(),
			                   Boolean.FALSE, message),
			new HttpHeaders(), HttpStatus.OK);
	}
	
	public <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
		return new ResponseEntity<>(
			new ApiResponse<T>(null, ApiResponseCode.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.value(),
			                   Boolean.FALSE, message),
			new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
		return new ResponseEntity<>(
				new ApiResponse<T>(null, ApiResponseCode.DUPLICATE_ENTITY.toString(), HttpStatus.CONFLICT.value(),
						Boolean.FALSE, message),
				new HttpHeaders(), HttpStatus.CONFLICT);
	}

	public <T> ResponseEntity<ApiResponse<T>> conflict(T t,String message) {
		return new ResponseEntity<>(
				new ApiResponse<T>(t, ApiResponseCode.DUPLICATE_ENTITY.toString(), HttpStatus.CONFLICT.value(),
						Boolean.FALSE, message),
				new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
}
