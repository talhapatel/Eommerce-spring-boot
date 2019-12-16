package com.ecommerce.globalExeption;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ecommerce.controller.ApiResponse;
import com.ecommerce.controller.BaseController;
import com.ecommerce.controller.GoMessage;
import com.ecommerce.controller.GoMessageType;
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Scope("request")

@ControllerAdvice

public class GlobalExceptionHandler extends BaseController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	  @ExceptionHandler(value = AccessDeniedException.class) public ApiResponse
	  AccessDeniedHandler(AccessDeniedException e) { logger.error(e.getMessage(),
	  e); addFailure(GoMessageType.UNAUTHORIZED, e.getMessage()); return
	  renderResponse(); }
	  


		@ExceptionHandler(value = AuthorizationException.class)
		public ApiResponse AuthorizationExceptionHandler(AuthorizationException e) {
			logger.error(e.getMessage(), e);
			addFailure(GoMessageType.UNAUTHORIZED, e.getMessage());
			return renderResponse();
		}

		@ExceptionHandler(value = MissingServletRequestParameterException.class)
		public ApiResponse nfeHandler(MissingServletRequestParameterException e) {
			logger.error(e.getMessage(), e);
			String[] param = { e.getParameterName() };
			addFailure(GoMessageType.PARAM_REQUIRED, param);
			return renderResponse();
		}

		/*
		 * @ExceptionHandler(value = MethodArgumentTypeMismatchException.class) public
		 * ApiResponse nfeHandler(MethodArgumentTypeMismatchException e) {
		 * logger.error(e.getMessage(), e); String[] param = { e.getName() };
		 * addFailure(GoMessageType.PARAM_TYPE_MISMATCH, param); return
		 * renderResponse(); }
		 */

		@ExceptionHandler(MethodArgumentTypeMismatchException.class)
		protected ApiResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
			addFailure(GoMessageType.EXCEPTION_OCCURED,
					String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(),
							ex.getValue(), ex.getRequiredType().getSimpleName()));
			return renderResponse();
		}


	

		@ExceptionHandler(value = { FileSizeLimitExceededException.class })
		public ApiResponse handleFileSizeExceedException(Exception e) {
			logger.error(e.getMessage(), e);
			addFailure(GoMessageType.EXCEPTION_OCCURED, "File size exceeded");
			return renderResponse();
		}

		@ExceptionHandler(value = { MultipartException.class })
		public ApiResponse handleMultipartException(Exception e) {
			logger.error(e.getMessage(), e);
			addFailure(GoMessageType.EXCEPTION_OCCURED, "Can not process this request");
			return renderResponse();
		}

		@ExceptionHandler(value = { GoException.class })
		public ApiResponse handleGoException(GoException e) {
			logger.error(e.getMessage(), e);
			for (GoMessage message : e.getGoMessages()) {
				addFailure(message);
			}
			return renderResponse();
		}

		@ExceptionHandler(EntityNotFoundException.class)
		protected ApiResponse handleEntityNotFound(EntityNotFoundException e) {
			addFailure(GoMessageType.EXCEPTION_OCCURED, e.getMessage());
			return renderResponse();
		}

		@ExceptionHandler(javax.validation.ConstraintViolationException.class)
		protected ApiResponse handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
			addFailure(GoMessageType.EXCEPTION_OCCURED, ex.getConstraintViolations().toString());
			return renderResponse();
		}

		protected ApiResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
				HttpStatus status, WebRequest request) {
			addFailure(GoMessageType.EXCEPTION_OCCURED, ex.getBindingResult().getFieldErrors().toString());
			addFailure(GoMessageType.EXCEPTION_OCCURED, ex.getBindingResult().getGlobalErrors().toString());
			return renderResponse();
		}

		protected ApiResponse handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
				HttpStatus status, WebRequest request) {
			addFailure(GoMessageType.EXCEPTION_OCCURED,
					String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
//	        apiError.setDebugMessage(ex.getMessage());
			return renderResponse();
		}

		@ExceptionHandler(DataIntegrityViolationException.class)
		protected ApiResponse handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
			if (ex.getCause() instanceof ConstraintViolationException) {
				addFailure(GoMessageType.EXCEPTION_OCCURED, "Database error: " + HttpStatus.CONFLICT);
//		            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
			}
			addFailure(GoMessageType.EXCEPTION_OCCURED, HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return renderResponse();
//		        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
		}

		@ExceptionHandler(value = { Exception.class, Error.class })
		public ApiResponse handleException(Exception e) {
			logger.error(e.getMessage(), e);
			addFailure(GoMessageType.EXCEPTION_OCCURED, e.getMessage());
			return renderResponse();
		}
}
