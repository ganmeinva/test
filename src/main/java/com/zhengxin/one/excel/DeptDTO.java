package com.zhengxin.one.excel;

import com.alibaba.excel.annotation.ExcelProperty;

public class DeptDTO {
    @ExcelProperty(value = "字符串标题*", index = 0)
    private String string1;
    @ExcelProperty(value = "字符串标题1*", index = 4)
    private String string2;

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    @Override
    public String toString() {
        return "DeptDTO{" +
                "string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                '}';
    }
}
