package com.example.employee.exception;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class ErrorDetails {

    private Integer status;
    private String message;
}
