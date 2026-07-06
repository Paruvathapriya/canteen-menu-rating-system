package com.example.canteen.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
    var errors = ex.getBindingResult().getFieldErrors().stream()
      .map(e -> Map.of("field", e.getField(), "message", e.getDefaultMessage()))
      .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(Map.of("errors", errors));
  }
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntime(RuntimeException ex){
    return ResponseEntity.status(409).body(Map.of("error", ex.getMessage()));
  }
}
