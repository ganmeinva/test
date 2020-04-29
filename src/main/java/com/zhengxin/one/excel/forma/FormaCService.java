package com.zhengxin.one.excel.forma;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.zhengxin.one.excel.AexeclDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class FormaCService {

    //读方法
    @Async
    public void read(InputStream inputStream,String uuid){
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream).build();
            //  自己使用功能必须不同的Listener(第四行开始读)-这里监听器用泛型就不需要建多个了

            ReadSheet readSheet1 =
                    EasyExcel.readSheet(1).head(ExcelInfoOneDTO.class).registerReadListener(new FormanReadListener(new FormaSService(uuid))).headRowNumber(4).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(2).head(ExcelInfoTwoDTO.class).registerReadListener(new FormanReadListeners(new FormaSService(uuid))).headRowNumber(4).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去（按excel中的顺序读）
            excelReader.read(readSheet1,readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
}
