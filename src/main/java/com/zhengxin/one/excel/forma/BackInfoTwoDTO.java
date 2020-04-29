package com.zhengxin.one.excel.forma;

import com.alibaba.excel.annotation.ExcelProperty;

//下载模板时需要填充数据的DTO
public class BackInfoTwoDTO {
    //字段和模板对应,模板中+{。XXX}
    @ExcelProperty(value = "序号", index = 1)
    public Integer id;
    @ExcelProperty(value = "姓名", index = 2)
    public String staffName;
    @ExcelProperty(value = "部门编号", index = 3)
    public String deptName;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BackInfoTwoDTO{" +
                "id=" + id +
                ", staffName='" + staffName + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
