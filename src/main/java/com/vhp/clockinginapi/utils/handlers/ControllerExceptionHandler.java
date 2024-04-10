package com.vhp.clockinginapi.utils.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vhp.clockinginapi.dtos.ApiResponseDTO;
import com.vhp.clockinginapi.utils.exceptions.AlreadyInLunchBreakException;
import com.vhp.clockinginapi.utils.exceptions.BusinessException;
import com.vhp.clockinginapi.utils.exceptions.DatePreviousThanLastRegisteredException;
import com.vhp.clockinginapi.utils.exceptions.ErrorDTO;
import com.vhp.clockinginapi.utils.exceptions.LoginAlreadyExists;
import com.vhp.clockinginapi.utils.exceptions.LunchTimeBreakException;
import com.vhp.clockinginapi.utils.exceptions.ResourceNotFoundException;
import com.vhp.clockinginapi.utils.exceptions.UserNoLunchBreakException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> businessException(BusinessException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                exception.getHttpStatusCode().value(),
                "Business error",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> notFound(ResourceNotFoundException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler({ TransactionSystemException.class })
    protected ResponseEntity<ApiResponseDTO<ErrorDTO>> handlePersistenceException(Exception ex,
            HttpServletRequest request) {
        logger.info(ex.getClass().getName());

        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException consEx) {
            final List<String> errors = new ArrayList<>();
            for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
                errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }

            final var err = new ErrorDTO(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Erro ao salvar dados.",
                    errors.toString(),
                    request.getRequestURI());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO<ErrorDTO>(
                    false,
                    "Error: " + ex.getMessage(),
                    null,
                    err));
        }
        return internalErrorException(ex, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> accessDeniedException(AccessDeniedException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Access denied",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(AlreadyInLunchBreakException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> alreadyInLunchBreakException(AlreadyInLunchBreakException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "The new checkpoint cannot be a lunch time, because the last one was too.",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(LunchTimeBreakException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> lunchTimeBreak(LunchTimeBreakException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Still in luch break time",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(UserNoLunchBreakException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> userNoLunchBreakException(UserNoLunchBreakException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "This user cannnot take a break of the type lunch break.",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(DatePreviousThanLastRegisteredException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> datePreviousThanLastRegisteredException(DatePreviousThanLastRegisteredException exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Cannot register a date in the past.",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(LoginAlreadyExists.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> loginAlreadyExists(LoginAlreadyExists exception,
            HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "User already exists",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> internalErrorException(Exception e, HttpServletRequest request) {

        var err = new ErrorDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected problem occurred.",
                e.getMessage(),
                request.getRequestURI());

        logger.error("An unexpected problem occurred. ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<ErrorDTO>(
                false,
                "Error: " + e.getMessage(),
                null,
                err));
    }
}
