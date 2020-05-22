package cn.ac.ict.controller;

import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SosBLEGatewayService;
import cn.ac.ict.vo.GatewayDataVO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ble/data")
@Api(value = "蓝牙网关api", tags = {"蓝牙网关api"})
public class BLEGatewayController {

    @Resource
    private SosBLEGatewayService sosBLEGatewayService;

    @ApiOperation(value = "蓝牙网关接收数据")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    public void bleDataUpload(HttpServletRequest request) {
        try {
            // 获取 网关传送来 的 原数据并保存
            List<GatewayDataVO> gatewayDataVOList = readJSONString(request);
            sosBLEGatewayService.bleDataUpload(gatewayDataVOList);
        } catch (GlobalException e) {
            log.info("[蓝牙网关接收数据]失败,原因：{}", e.getMsg());
        } catch (Exception e) {
            log.info("[蓝牙网关接收数据]失败,原因：{}", e.getMessage());
        }
    }


    /**
     * 读取流中的数据,并封装成 GatewayDataVO
     *
     * @param request
     * @return
     */
    private List<GatewayDataVO> readJSONString(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            throw new GlobalException(AppCodeInfo.SYS_ERROR.getCode(), e.getMessage());
        }
        // 格式转换
        List<GatewayDataVO> result = new ArrayList<>();
        List list = JSON.parseObject(stringBuffer.toString(), List.class);
        for (int i = 0; i < list.size(); i++) {
            GatewayDataVO entity = JSON.parseObject(JSON.toJSONString(list.get(i)), GatewayDataVO.class);
            result.add(entity);
        }
        return result;
    }
}
