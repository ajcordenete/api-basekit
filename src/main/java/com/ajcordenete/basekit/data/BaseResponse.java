package com.ajcordenete.basekit.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    private Boolean success;

    private Integer status;

    private String message;
}
