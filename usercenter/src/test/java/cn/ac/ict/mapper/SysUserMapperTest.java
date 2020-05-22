package cn.ac.ict.mapper;

import cn.ac.ict.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserMapperTest {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void selectById() {
        SysUserEntity sysUserEntity = sysUserMapper.selectById("8f439adb33f444a192b7e6d9652802a7");
        System.out.println(sysUserEntity.toString());
    }

}
