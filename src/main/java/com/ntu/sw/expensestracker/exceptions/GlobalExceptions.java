package com.ntu.sw.expensestracker.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ntu.sw.expensestracker.services.CategoryServiceImpl;

@ControllerAdvice
public class GlobalExceptions {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptions.class);


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
        logger.error("🔴 " + ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistException(Exception ex) {
        logger.error("🔴 " + ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(Exception ex) {
        logger.error("🔴 " + ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException ex) {
        logger.error("🔴 " + ex.getMessage(), ex);        
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpenseNotFound.class)
    public ResponseEntity<ErrorResponse> handleExpenseNotFoundException(ExpenseNotFound e) {
        logger.error("🔴 " + e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    // VALIDATION EXCEPTION HANDLER
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

    // Get the list of validation errors
    List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();

    // Create a Stringbuild to store all error messages
    StringBuilder sb = new StringBuilder();

    // Loop and append the errors
    for (ObjectError error : validationErrors) {
      sb.append(error.getDefaultMessage() + ". ");
    }
    logger.error("🔴 " + sb.toString());
    ErrorResponse errorResponse = new ErrorResponse(sb.toString(), LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        logger.error("🔴 " + ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong 😞", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        logger.error("🔴 " + ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Item does not exist.", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
