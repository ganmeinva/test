package com.zhengxin.one.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;

import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "excel")
public class ExcelC {
    //数据生成
    private List<AexeclDTO> data() {
        List<AexeclDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AexeclDTO data = new AexeclDTO();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    //根据模板写
    @RequestMapping(value = "a",method = RequestMethod.GET)
    public void insertBase(HttpServletResponse response)throws IOException{
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        File file = new File("src/main/resources/template/aa.xlsx");
        EasyExcel.write(response.getOutputStream(), AexeclDTO.class).withTemplate(file.getAbsolutePath()).sheet().doWrite(data());
    }


    //案例 写入，导出
    @GetMapping("b")
    public void download(HttpServletResponse response) throws IOException {

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), AexeclDTO.class).sheet("模板2").doWrite(data());

    }
    //案例 读取，再填充
    @GetMapping("bb")
    public void readAndWrite(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), AexeclDTO.class, new UploadDataListener(new AexcelServer())).sheet("模板2").doRead();
    }
    //上传解析数据
    @PostMapping("c")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), AexeclDTO.class, new UploadDataListener(new AexcelServer())).sheet("模板2").doRead();
        return "success";
    }
    //读本地模板文件
    @GetMapping("d")
    @ResponseBody
    public String goSart() throws IOException {
        File file = new File("src/main/resources/template/aa.xlsx");
        System.out.println(file.getAbsolutePath());
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭 (headRowNumber指定行读)
        EasyExcel.read(file.getAbsolutePath(), AexeclDTO.class, new UploadDataListener(new AexcelServer())).sheet().headRowNumber(1).doRead();
        return "success";
    }
    //填充excel模板信息
    @GetMapping("e")
    @ResponseBody
    public String goIn() throws IOException {
        File file = new File("src/main/resources/template/bb.xlsx");
        String fileName="demo.xlsx";
        ArrayList<AexeclDTO> aexeclDTOList = new ArrayList<>();
        AexeclDTO aexeclDTO = new AexeclDTO();
        for (int i = 0; i < 10; i++) {
            aexeclDTO.setDoubleData(2.00);
            aexeclDTO.setString("11");
            aexeclDTO.setDate(Date.from(Instant.now()));
            aexeclDTO.setIgnore("nihao"+i);
            aexeclDTOList.add(aexeclDTO);
        }
        EasyExcel.write(fileName).withTemplate(file.getAbsolutePath()).sheet("模板2").doFill(aexeclDTOList);
        return "success";
    }

    //全部sheet读
    @GetMapping("f")
    @ResponseBody
    public String goReadAll() throws IOException {
        File file = new File("src/main/resources/template/aa.xlsx");
        System.out.println(file.getAbsolutePath());
        // 读取全部sheet
        EasyExcel.read(file.getAbsolutePath(), AexeclDTO.class, new UploadDataListener(new AexcelServer())).doReadAll();
        return "success";
    }

    //多个sheet读
    @GetMapping("g")
    @ResponseBody
    public String goRead(){
        File file = new File("src/main/resources/template/aa.xlsx");
        System.out.println(file.getAbsolutePath());
        // 读取部分sheet
        String fileName = file.getAbsolutePath();
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();

            //  自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet("模板").head(AexeclDTO.class).registerReadListener(new UploadDataListener(new AexcelServer())).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet("模板2").head(AexeclDTO.class).registerReadListener(new UploadDataListeners(new AexcelServer())).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        return "success";
    }
    //填充导出(单个sheet)
    @GetMapping("h")
    public void inDownload(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        File file = new File("src/main/resources/template/bb.xlsx");
        ArrayList<AexeclDTO> aexeclDTOList = new ArrayList<>();
        AexeclDTO aexeclDTO = new AexeclDTO();
        for (int i = 0; i < 10; i++) {
            aexeclDTO.setDoubleData(2.00);
            aexeclDTO.setString("11");
            aexeclDTO.setDate(Date.from(Instant.now()));
            aexeclDTO.setIgnore("nihao"+i);
            aexeclDTOList.add(aexeclDTO);
        }
        EasyExcel.write(response.getOutputStream()).withTemplate(file.getAbsolutePath()).sheet("模板2").doFill(aexeclDTOList);
    }
    //填充导出(多个sheet)
    @GetMapping("y")
    public void inDownloads(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        File file = new File("src/main/resources/template/bb.xlsx");
        ArrayList<AexeclDTO> aexeclDTOList = new ArrayList<>();
        AexeclDTO aexeclDTO = new AexeclDTO();
        for (int i = 0; i < 10; i++) {
            aexeclDTO.setDoubleData(2.00);
            aexeclDTO.setString("11");
            aexeclDTO.setDate(Date.from(Instant.now()));
            aexeclDTO.setIgnore("nihao" + i);
            aexeclDTOList.add(aexeclDTO);
        }

        DeptDTO dto1 = new DeptDTO();
        dto1.setString1("fajisfjio\ndasdjaisojdoi\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");
        dto1.setString2("fasjfoijasoijfoiasjiofjoiasjifjoiasj\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");

        ExcelWriter build = EasyExcel.write(response.getOutputStream()).withTemplate(file.getAbsolutePath()).build();
         WriteSheet build1 = EasyExcel.writerSheet(1).build();
         WriteSheet build2 = EasyExcel.writerSheet(2).build();
        build.fill(dto1,build2);
        build.fill(aexeclDTOList,build1).finish();

       /* EasyExcel.write(response.getOutputStream()).withTemplate(file.getAbsolutePath()).sheet("模板3").doFill(dto1);*/

    }
    //填充导出(单个sheet)
    @GetMapping("z")
    public void inDown(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        File file = new File("src/main/resources/template/bb.xlsx");

        DeptDTO dto = new DeptDTO();
        dto.setString1("fajisfjio\ndasdjaisojdoi\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");
        dto.setString2("fasjfoijasoijfoiasjiofjoiasjifjoiasj\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");

        EasyExcel.write(response.getOutputStream()).withTemplate(file.getAbsolutePath()).sheet("模板3").doFill(dto);

    }
    //填充生成excel临时文件
    @GetMapping("zz")
    public void inDowns() throws FileNotFoundException {
        //\n可以换行
        DeptDTO dto = new DeptDTO();
        dto.setString1("fajisfjio\ndasdjaisojdoi\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");
        dto.setString2("fasjfoijasoijfoiasjiofjoiasjifjoiasj\nffjsaoijfoiasjof\nnfasifsaoijfoiasj");

        File file = new File("src/main/resources/template/bb.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/template/cc.xlsx");
        EasyExcel.write(fileOutputStream).withTemplate(file.getAbsolutePath()).sheet("模板3").doFill(dto);
    }
    //删除上传后的临时文件
    @GetMapping("zzz")
    public void inDownss()  {
        //这里肯定要判断有没有这个文件什么的 你们自己写判断逻辑
        File file = new File("src/main/resources/template/cc.xlsx");
        file.delete();
    }


}
