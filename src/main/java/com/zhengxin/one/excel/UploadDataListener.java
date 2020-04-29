package com.zhengxin.one.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;


import java.util.ArrayList;
import java.util.List;


public class UploadDataListener extends AnalysisEventListener<AexeclDTO> {

    private AexcelServer aexcelServer;


    private List<AexeclDTO> cachedDataList = new ArrayList<>();

    public UploadDataListener(AexcelServer aexcelServer) {
        this.aexcelServer = aexcelServer;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(AexeclDTO data, AnalysisContext analysisContext) {
        System.out.println(data);
        //放入list临时缓存数据
        cachedDataList.add(data);

    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("kan1");
        //解析完执行的操作
        aexcelServer.write(cachedDataList);
    }
}
