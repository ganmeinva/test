package com.zhengxin.one.excel.forma;

import com.alibaba.excel.annotation.ExcelProperty;

//对应全部excel的字段(sheet2)
public class ExcelInfoTwoDTO {
    @ExcelProperty(value = "校验提示", index = 0)
    public String checkInfo;
    @ExcelProperty(value = "序号", index = 1)
    public Integer id;
    @ExcelProperty(value = "姓名", index = 2)
    public String staffName;
    @ExcelProperty(value = "部门名称", index = 3)
    public String deptName;
    @ExcelProperty(value = "手机号", index = 4)
    public String phone;

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ExcelInfoTwoDTO{" +
                "checkInfo='" + checkInfo + '\'' +
                ", id=" + id +
                ", staffName='" + staffName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
