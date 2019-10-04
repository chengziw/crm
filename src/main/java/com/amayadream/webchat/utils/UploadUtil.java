package com.amayadream.webchat.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * FileName: UploadUtil
 * Author:  wangzicheng
 * Date:     2019/10/1 0001 16:10
 * Description: 文件上传
 * History:TODO 文件服务器
 */
public class UploadUtil {

    /**
     * Spring MVC文件上传,返回的是经过处理的path+fileName
     *
     * @param request request
     * @param folder  上传文件夹
     * @param name  用户名
     * @return
     */
    public String upload(HttpServletRequest request, String folder, String name) {
        FileUtil fileUtil = new FileUtil();
        String file_url = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String prefix = fileUtil.getFilePrefix(file);
                if (file != null) {
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName = name + "." + prefix;
                        //定义上传路径,格式为 upload/Amayadream/Amayadream.jpg
                        // String path = request.getServletContext().getRealPath("/") + folder + "/" + name;
                        String path = getPath() + folder + "/" + name;
                        File localFile = new File(path, fileName);
                        if (!localFile.exists()) {
                            localFile.mkdirs();
                        }
                        try {
                            file.transferTo(localFile);
                            file_url = folder + "/" + name + "/" + fileName;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return file_url;
    }


    public static String getPath() {
        String system = System.getProperty("os.name");
        if (system.startsWith("Windows")) { // Windows系统，用于本地测试
            return "D:\\\\tmp\\\\";
        } else if ("Linux".equalsIgnoreCase(system)) { // linux 系统
            return "/tmp/";
        }
        return "/tmp/";
    }

    public static String getHtmlPath() {
        String system = System.getProperty("os.name");
        if (system.startsWith("Windows")) { // Windows系统，用于本地测试
            return "D:/tmp";
        } else if ("Linux".equalsIgnoreCase(system)) { // linux 系统
            return "/tmp/";
        }
        return "/tmp/";
    }
}
