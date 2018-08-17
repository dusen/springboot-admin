package com.fastretailing.dcp.common.web.handler;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class User {


    @NotNull
    private String field1;

    @NotEmpty
    private String field2;

}
