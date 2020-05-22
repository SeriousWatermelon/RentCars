package cn.ac.ict.mapper;

import cn.ac.ict.entity.TtSosBindEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TtSosBindMapper extends BaseMapper<TtSosBindEntity> {

    /**
     * 查询所有被绑定的mac地址
     *
     * @return
     */
    List<String> getMacBind(@Param("bindStatus") String bindStatus);

}
