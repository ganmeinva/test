package com.zhengxin.one.esStudy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengxin.one.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1. @ClassName PeoPle
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/5/17 11:05
 * 5. @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class People  extends BaseEntity {
    private String name;
    @JsonIgnore
    private int sex;
    private int age;
}
