package cn.ac.ict.controller;

import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.utils.FileUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 保证 apk下载链接 和之前的一样
 *
 * @Author: WeiMan Cui
 * @Date: 2020/5/13 22:58
 * @Description:
 */
@Slf4j
@RestController
public class AppDownLoadController {

    @ApiOperation(value = "下载安卓安装包")
    @GetMapping(value = "/upload/SeawayS1.apk")
    public void downloadApp(HttpServletRequest request, HttpServletResponse response) {
        try {
            FileUtil.downloadFile(request, response);
        } catch (GlobalException e) {
            log.info("[下载安卓安装包]失败,原因：{}", e.getMsg());
        } catch (Exception e) {
            log.info("[下载安卓安装包]失败,原因：{}", e.getMessage());
        }
    }

}
