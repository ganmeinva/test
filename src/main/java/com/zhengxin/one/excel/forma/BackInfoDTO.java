package com.zhengxin.one.excel.forma;

import com.alibaba.excel.annotation.ExcelProperty;

//下载模板时需要填充数据的DTO
public class BackInfoDTO {
    //字段和模板对应,模板中+{。XXX}
    @ExcelProperty(value = "序号", index = 1)
    public Integer id;
    @ExcelProperty(value = "姓名", index = 2)
    public String staffName;
    @ExcelProperty(value = "员工编号", index = 3)
    public Integer staffId;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BackInfoDTO{" +
                "id=" + id +
                ", staffName='" + staffName + '\'' +
                ", staffId=" + staffId +
                '}';
    }
}
