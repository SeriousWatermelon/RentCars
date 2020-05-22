package cn.ac.ict.service;

import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.vo.GatewayDataVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SosBLEGatewayServiceTest {

    @Resource
    private SosBLEGatewayService service;

    @Test
    public void bleDataUpload() {
        List<GatewayDataVO> dataList = createData();
        service.bleDataUpload(dataList);
    }

    private List<GatewayDataVO> createData() {
        List<GatewayDataVO> voList = new ArrayList<>();
        GatewayDataVO vo1 = new GatewayDataVO();
        vo1.setTimestamp(DateUtil.nowDate());
        vo1.setType("Gateway");
        vo1.setMac("AC233FC069BA");
        vo1.setGatewayFree(94.0f);
        vo1.setGatewayLoad(1.1f);
        voList.add(vo1);

        GatewayDataVO vo2 = new GatewayDataVO();
        // vo2.setTimestamp(DateUtil.nowDate());
        vo2.setTimestamp(DateUtil.parse("2020-05-15 09:05:16"));
        vo2.setType("Unknown");
        vo2.setMac("C967F565D9ED");
        vo2.setBleName("YT1 9ED");
        vo2.setRssi(-52);
        vo2.setRawData("02010612FF107803E81121140392021F004F00242900080959543120394544");
        voList.add(vo2);

        GatewayDataVO vo3 = new GatewayDataVO();
        // vo3.setTimestamp(DateUtil.nowDate());
        vo3.setTimestamp(DateUtil.parse("2020-05-15 09:05:16"));
        vo3.setType("Unknown");
        vo3.setMac("CC98C30E477B");
        vo3.setBleName("YT1 77B");
        vo3.setRssi(-47);
        vo3.setRawData("02010612FF107803E811212700280002001200232500080959543120373742");
        voList.add(vo3);

        return voList;
    }
}
