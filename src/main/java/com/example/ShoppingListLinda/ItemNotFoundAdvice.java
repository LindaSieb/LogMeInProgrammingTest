package com.example.ShoppingListLinda;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ItemNotFoundAdvice {

  @ResponseBody											//goes straight to response body (console)
  @ExceptionHandler(ItemNotFoundException.class) 		//pairs this class with the ItemNotFoundException class
  @ResponseStatus(HttpStatus.NOT_FOUND) 				//makes sure the 404 error is sent.
  String employeeNotFoundHandler(ItemNotFoundException ex) {
    return ex.getMessage();
  }
}
