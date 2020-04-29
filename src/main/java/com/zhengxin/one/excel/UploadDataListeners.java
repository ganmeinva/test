package com.zhengxin.one.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;


public class UploadDataListeners extends AnalysisEventListener<AexeclDTO> {

    private AexcelServer aexcelServer;

    public UploadDataListeners(AexcelServer aexcelServer) {
        this.aexcelServer = aexcelServer;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(AexeclDTO data, AnalysisContext analysisContext) {
        System.out.println(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("kan2");
    }
}
