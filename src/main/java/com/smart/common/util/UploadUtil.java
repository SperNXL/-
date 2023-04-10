package com.smart.common.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class UploadUtil {
    //案例域名
    public static final String ALI_DOMAIN="https://picbedspernxl.oss-cn-chengdu.aliyuncs.com/";

    public static String uploadImage(MultipartFile file) throws IOException {
        //生成文件名
        String originalFileName=file.getOriginalFilename();
        String ext="."+ FilenameUtils.getExtension(originalFileName);
        String uuid= UUID.randomUUID().toString().replace("-","");
        String fileName=uuid+ext;

        //地域节点
        String endpoint="http://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId="LTAI5t94TsVFPX6C4EdMfzor";
        String accessKeySecret="UyZRFqhUWDD2rYJxJkzI8ym7ffw8Kn";
        //OSS客户端对象
        OSS ossClient=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject("picbedspernxl",
                fileName,
                file.getInputStream()
        );
        ossClient.shutdown();

        return ALI_DOMAIN+fileName;
    }

}
