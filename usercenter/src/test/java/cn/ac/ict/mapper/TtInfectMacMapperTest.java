package cn.ac.ict.mapper;

import cn.ac.ict.dto.TtCloseContactDTO;
import cn.ac.ict.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TtInfectMacMapperTest {

    @Resource
    private TtInfectMacMapper mapper;

    @Test
    public void listCloseContacts() {
        String userName = "崔为满";
        Date start = DateUtil.parse("2020-05-06 00:00:00");
        Date end = DateUtil.parse("2020-05-09 00:00:00");
        List<TtCloseContactDTO> result = mapper.listCloseContacts(userName, start, end);
        result.forEach(System.out::println);
    }
}
