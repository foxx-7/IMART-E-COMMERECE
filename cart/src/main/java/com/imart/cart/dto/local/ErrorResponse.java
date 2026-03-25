package com.imart.cart.dto.local;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
   private LocalDateTime timestamp;
   private String message;
   private String error;
   private HttpStatus status;

}
