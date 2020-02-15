package com.yunding.answer.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author ycSong
 * @version 1.0
 * @date 2019/7/28 15:15
 */
@Component
public class OSSUtil {
    private static String bucketName = "yc-song";
    /**
     *
     * @Title: getOSSClient
     * @Description: 获取oss客户端
     * @return OSSClient oss客户端
     * @throws
     */
    public static OSSClient getOSSClient() {
        //使用你的对应的endpoint地址
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAItfWG6CIVYoDa";
        String accessKeySecret = "GBIN9dCRWS0hA1q6M8podry7nN80vS";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    /**
     *
     * @Title: uploadByNetworkStream
     * @Description: 通过网络流上传文件
     * @param ossClient 	oss客户端
     * @param url 			URL
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名）例如“test/index.html”
     * @return void 		返回类型
     * @throws
     */
    public static void uploadByNetworkStream(OSSClient ossClient, URL url, String bucketName, String objectName) {
        try {
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     *
     * @Title: uploadByInputStream
     * @Description: 通过输入流上传文件
     * @param ossClient 	oss客户端
     * @param inputStream 	输入流
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public static void uploadByInputStream(OSSClient ossClient, InputStream inputStream, String bucketName,
                                           String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     *
     * @Title: uploadByFile
     * @Description: 通过file上传文件
     * @param ossClient 	oss客户端
     * @param file 			上传的文件
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public static void uploadByFile(OSSClient ossClient, File file, String bucketName, String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }


    /**
     *
     * @Title: deleteFile
     * @Description: 根据key删除oss服务器上的文件
     * @param ossClient		oss客户端
     * @param bucketName		bucket名称
     * @param key    		文件路径/名称，例如“test/a.txt”
     * @return void    		返回类型
     * @throws
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     *
     * @Title: getInputStreamByOSS
     * @Description:根据key获取服务器上的文件的输入流
     * @param ossClient 	oss客户端
     * @param bucketName 	bucket名称
     * @param key 			文件路径和名称
     * @return InputStream 	文件输入流
     * @throws
     */
    public static InputStream getInputStreamByOSS(OSSClient ossClient, String bucketName, String key) {
        InputStream content = null;
        try {
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     *
     * @Title: queryAllObject
     * @Description: 查询某个bucket里面的所有文件
     * @param ossClient		oss客户端
     * @param bucketName		bucket名称
     * @return List<String>  文件路径和大小集合
     * @throws
     */
    public static List<String> queryAllObject(OSSClient ossClient, String bucketName) {
        List<String> results = new ArrayList<String>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                results.add(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return results;
    }


    /**
     * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。
     *
     * @param imageFile
     * @return
     */
    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            img=null;
        }
    }

    public static File tranFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        String uuidTemp = UUID.randomUUID().toString();
        String uuid = uuidTemp.replace("-", "");
        File imageFile = File.createTempFile(uuid, prefix);
        file.transferTo(imageFile);
        return imageFile;
    }

}

