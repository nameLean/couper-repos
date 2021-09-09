package com.example.test8_11.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class FileUtil {


    @Autowired
    private OSS oss;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    public  List<String> getName(MultipartFile[] multipartFile){
        List<String> collect =
                Arrays.stream(multipartFile).map(this::getName).collect(Collectors.toList());
        return collect;
    }
    @Async("getAsyncExecutor")
    public void upload(byte[] bytes,String fileName){
        String substring = fileName.substring(fileName.lastIndexOf("."));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getcontentType(substring));
        objectMetadata.setContentDisposition("inline");
        oss.putObject("cdqf2104",fileName,new ByteArrayInputStream(bytes),objectMetadata);

    }
    @Async("getAsyncExecutor")
    public void upload(MultipartFile multipartFile[],List<String> list){
        int length = multipartFile.length;
        IntStream.range(0,length).forEach(i->upload(multipartFile[i],list.get(i)));
    }
    @Async("getAsyncExecutor")
    public void upload(MultipartFile multipartFile,String name){
        String substring = name.substring(name.lastIndexOf("."));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getcontentType(substring));
        objectMetadata.setContentDisposition("inline");
        try {
            oss.putObject("cdqf2104",name,multipartFile.getInputStream(),objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public  String getName(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        long l = snowflakeIdWorker.nextId();
        return l+suffix;
    }

    public String upload(MultipartFile multipartFile){
        String upload;
        try {
             upload = upload(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
             return upload;
        } catch (IOException e) {
            log.error("文件上传异常");
            throw new RuntimeException(e);
        }
    }
    public String upload(String finalName,MultipartFile multipartFile){
        String upload;
        try {
            upload = upload(finalName,multipartFile.getOriginalFilename(),multipartFile.getInputStream());
            return upload;
        } catch (IOException e) {
            log.error("文件上传异常");
            throw new RuntimeException(e);
        }
    }
    public String upload(String finalName,String fileName,InputStream inputStream){

        //.jpg
        String substring = fileName.substring(fileName.lastIndexOf("."));
        String finalName2 = finalName + substring;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getcontentType(substring));
        objectMetadata.setContentDisposition("inline");
        oss.putObject("cdqf2104",finalName2,inputStream,objectMetadata);
        return finalName2;
    }
    public String upload(String fileName,InputStream inputStream){
        //.jpg
        String substring = fileName.substring(fileName.lastIndexOf("."));
        String finalName = snowflakeIdWorker.nextId() + substring;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getcontentType(substring));
        objectMetadata.setContentDisposition("inline");
        //这里是文件上传到OSS时为文件划分的文件夹名称
        oss.putObject("cdqf2104",finalName,inputStream,objectMetadata);
        return finalName;
    }


    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
