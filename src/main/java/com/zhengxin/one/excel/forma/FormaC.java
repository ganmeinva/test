package com.zhengxin.one.excel.forma;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "excel")
public class FormaC {


    @Autowired
    public FormaCService formaCService;

    @GetMapping
    public  String test(){
        return "haha";
    }

    //填充数据生成sheet1
    private List<BackInfoDTO> data1() {
        List<BackInfoDTO> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            BackInfoDTO data = new BackInfoDTO();
            data.setId(i);
            data.setStaffName("字符串" + i);
            data.setStaffId(i);
            list.add(data);
        }
        return list;
    }
    //填充数据生成sheet2
    private List<BackInfoTwoDTO> data2() {
        List<BackInfoTwoDTO> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            BackInfoTwoDTO data = new BackInfoTwoDTO();
            data.setId(i);
            data.setStaffName("姓名" + i);
            data.setDeptName("部门名称"+i);
            list.add(data);
        }
        return list;
    }

    //完整方法展示（多sheet读取，模板填充，模板为修改银行卡）
    //开始----
    //下载模板，填充数据到模板
    @GetMapping("o")
    //前端给我模板名字,和要带出谁的数据
    public void upFeild(HttpServletResponse response,@RequestParam String templateName,@RequestParam Integer staffId){
        //命名什么的自己规范
        try{
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode(templateName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        }catch (IOException i){
            //捕获异常
        }
        ExcelWriter build=null;
        try{
            File file = new File("src/main/resources/template/修改银行卡.xlsx");
            //多sheet填充(数据直接生成就不根据staffId查了)
             build = EasyExcel.write(response.getOutputStream()).withTemplate(file.getAbsolutePath()).build();
             for(int i=1; i<3;i++){
                 WriteSheet build1 = EasyExcel.writerSheet(i).build();
                 if(i==1){
                     build.fill(data1(),build1);
                 }else {
                     build.fill(data2(),build1);
                 }
             }
        }catch (Exception e){
            //捕获异常
        }finally {
            //关闭流
            if(build!=null){
                build.finish();
            }
        }
    }
    //模板下载好填好后，上传模板，读操作
    @GetMapping("oo")
    public String read (@RequestParam("file") MultipartFile file) {
        //生成一个uuid(给前端 让他用这个调结果)
        String uuid="11";
        //调自己的处理类中的读取方法
        try{
            //这里判断下它的模板第一个sheet的名字是否是SIE(如果不是让他去下载模板)
            List<ReadSheet> readSheets = EasyExcel.read(file.getInputStream()).build().excelExecutor().sheetList();
            System.out.println(readSheets.get(0).getSheetName());
            //异步
            formaCService.read(file.getInputStream(),uuid);
            //插入一条数据到数据库返回uuid给前端，前端拿这个轮询拿结果
            return uuid;
        }catch (Exception e){
            //抛异常
        }
        return uuid;
    }


    public static void main(String[] args) throws IOException {
//        FileInputStream inputStream=new FileInputStream("D:\\test.json");
//        FileOutputStream outputStream=new FileOutputStream("D:\\b.json");
//        FileChannel channelA = inputStream.getChannel();
//        FileChannel channelB = outputStream.getChannel();
//        channelB.transferFrom(channelA,0,Math.toIntExact(channelA.size()));
//        channelA.close();
//        channelB.close();
//        inputStream.close();
//        outputStream.close();


        //---------------------------------------
        RandomAccessFile randomAccessFile=new RandomAccessFile("D:\\test.json","rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte) 'G');
        channel.close();
        randomAccessFile.close();

    }
}
