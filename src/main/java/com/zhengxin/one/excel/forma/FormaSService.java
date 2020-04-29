package com.zhengxin.one.excel.forma;

import java.util.HashMap;
import java.util.List;


public class FormaSService {

    private String uuid;

    public FormaSService(String uuid) {
        this.uuid = uuid;
    }
    // 自己的方法
    public void saveOne(List<ExcelInfoOneDTO> list,Boolean status){
        //将这一页的数据保存(保存时先根据uuid查一下之前有没有保存,若保存了就查出来拼接再存进去)




    }
    // 自己的方法
    public void saveTwo(List<ExcelInfoTwoDTO> list,Boolean status){

        //如果是多sheet就将之前的保存的数据查出来

        //校验失败填充错误报告并上传并将上传地址等记录数据库(看ExcelC里写的——填充导出(多个sheet))

        //校验成功记录数据

    }

}
