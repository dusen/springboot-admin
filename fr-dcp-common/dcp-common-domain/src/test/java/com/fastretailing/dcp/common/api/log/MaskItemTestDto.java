package com.fastretailing.dcp.common.api.log;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude={"test2"})
public class MaskItemTestDto {
    public String test1 = "test1";
    public String test2 = "test2";
}
