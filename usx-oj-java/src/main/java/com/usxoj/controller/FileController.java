package com.usxoj.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;

import com.alibaba.excel.EasyExcel;
import com.usxoj.entity.Student;
import com.usxoj.entity.StudentExcel;
import com.usxoj.listener.StudentDataListener;
import com.usxoj.service.StudentExcelService;
import com.usxoj.service.StudentService;
import com.usxoj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Description:
 * date: 2021/8/9 19:40
 *
 * @author vinside
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentExcelService studentExcelService;

    /**
     * 下载模板
     */
    @GetMapping("/download")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/excelTemplates/";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("模板.xlsx", "utf-8"));
        byte[] bytes = FileUtil.readBytes(basePath + "模板.xlsx");//通过文件路径读取文件字节流
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获取上传文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //上传文件的绝对路径
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/excelTemplates/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);//把文件写入上传的路径

        // 有个很重要的点 StudentDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(rootFilePath, StudentExcel.class, new StudentDataListener(studentExcelService)).sheet().doRead();

        return Result.success(ip + ":" + port + "/excelTemplates/" + flag);//返回结果 url
    }

    @PostMapping("/uploadImg")
    public Result<?> uploadImg(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获取上传文件名称
        //定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //上传文件的绝对路径
        String rootFilePath = System.getProperty("user.dir") + "/target/classes/static/" + flag + "_" + originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);//把文件写入上传的路径
        return Result.success(ip + ":" + port + "/" + flag + "_" + originalFilename);//返回结果 url
    }


}
