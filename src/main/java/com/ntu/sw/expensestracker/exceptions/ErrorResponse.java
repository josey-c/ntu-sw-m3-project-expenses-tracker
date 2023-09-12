package com.ntu.sw.expensestracker.exceptions;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  private String message;
  private LocalDateTime timestamp;

  
}
