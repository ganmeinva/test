package com.zhengxin.one.excel.forma;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;


import java.util.ArrayList;
import java.util.List;

public class FormanReadListeners extends AnalysisEventListener<ExcelInfoTwoDTO> {

    private FormaSService formaSService;

    //全局数组缓存数据
    private List<ExcelInfoTwoDTO> cachedDataList = new ArrayList<>();

    //校验状态
    private Boolean status=false;


    public FormanReadListeners(FormaSService formaSService) {
        this.formaSService=formaSService;
    }
    //读每一行的数据都要来调这个方法
    @Override
    public void invoke(ExcelInfoTwoDTO excelInfoTwoDTO, AnalysisContext analysisContext) {
        //这里会读每一行数据,
        cachedDataList.add(excelInfoTwoDTO);
        //超过两百条数据，就先保存到数据库,清除list
        if(cachedDataList.size()>200){
            //超过两百条数据，就先保存到数据库,清除list
            //保存数据时记得传校验是成功还是失败的
            formaSService.saveTwo(cachedDataList,status);
            cachedDataList.clear();
        }
    }
    //整页读完调这个方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存数据时记得传校验是成功还是失败的
        formaSService.saveTwo(cachedDataList,status);
        System.out.println(2);
    }
}
