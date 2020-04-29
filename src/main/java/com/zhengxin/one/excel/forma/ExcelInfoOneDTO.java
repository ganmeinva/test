package com.zhengxin.one.excel.forma;

import com.alibaba.excel.annotation.ExcelProperty;

//对应全部excel的字段(sheet1)
public class ExcelInfoOneDTO {
    @ExcelProperty(value = "校验提示", index = 0)
    public String checkInfo;
    @ExcelProperty(value = "序号", index = 1)
    public Integer id;
    @ExcelProperty(value = "姓名", index = 2)
    public String staffName;
    @ExcelProperty(value = "员工编号", index = 3)
    public Integer staffId;
    @ExcelProperty(value = "开户银行", index = 4)
    public String bank;
    @ExcelProperty(value = "银行卡号", index = 5)
    public String bank_card;
    @ExcelProperty(value = "开户地", index = 6)
    public String open_base;

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

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getOpen_base() {
        return open_base;
    }

    public void setOpen_base(String open_base) {
        this.open_base = open_base;
    }

    @Override
    public String toString() {
        return "ExcelInfoOneDTO{" +
                "checkInfo='" + checkInfo + '\'' +
                ", id=" + id +
                ", staffName='" + staffName + '\'' +
                ", staffId=" + staffId +
                ", bank='" + bank + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", open_base='" + open_base + '\'' +
                '}';
    }
}
