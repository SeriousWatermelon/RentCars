package cn.ac.ict.utils;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件上传与下载
 */
@Slf4j
public class FileUtil {

    /**
     * app 升级 信息，app安装包上传
     *
     * @param file
     * @return
     */
    public static String saveAppUpgradeFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            log.info("path.getAbsolutePath={}", path.getAbsolutePath());
            File dest = new File(path.getAbsolutePath(), AppConstant.APP_UPGRADE_PATH + fileName);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            file.transferTo(dest);
            String downLoadPath = AppConstant.APP_UPGRADE_DOWNLOAD_PATH + fileName;
            return downLoadPath;
        } catch (IOException e) {
            throw new GlobalException(AppCodeInfo.FAILED);
        }
    }

    /**
     * 安卓安装包 下载
     *
     * @param request
     * @param response
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String path = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (System.getProperty("os.name").contains("dows")) {
                path = path.substring(1, path.length());
            }
            if (path.contains("jar")) {
                path = path.substring(0, path.lastIndexOf("."));
                path = path.substring(0, path.lastIndexOf("/"));
            }
            path = path.replace("target/classes/", "");
            path = path + AppConstant.APP_UPGRADE_PATH + "SeawayS1.apk";
            log.info("path={}", path);
            ClassPathResource resource = new ClassPathResource(path);
            InputStream in = resource.getInputStream();
            downFile("SeawayS1.apk", request, response, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downFile(String fileName, HttpServletRequest request,
                                HttpServletResponse response, InputStream in) throws IOException {
        try (OutputStream os = response.getOutputStream()) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream; charset=UTF-8");
            byte[] b = new byte[in.available()];
            in.read(b);
            os.write(b);
            os.flush();
        } catch (Exception e) {
            System.out.println("fileName=" + fileName);
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
