package cn.ac.ict.mapper;

import cn.ac.ict.entity.TtSosHeartRateEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TtSosHeartRateMapperTest {

    @Resource
    private TtSosHeartRateMapper mapper;

    @Test
    public void deleteByUserId() {
        String userId = "7c586dacc1ab4e24b0fc4dfaa4597bec";
        LambdaQueryWrapper<TtSosHeartRateEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosHeartRateEntity::getUserId, userId);
        int result = mapper.delete(query);
        System.out.println(result);
    }

}
