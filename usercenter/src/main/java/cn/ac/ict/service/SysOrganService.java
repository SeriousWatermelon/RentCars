package cn.ac.ict.service;

import cn.ac.ict.entity.SysOrganEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysOrganService extends IService<SysOrganEntity> {

    /**
     * 查询部门机构
     *
     * @param type 默认 -1，表示查询全部
     * @return
     */
    List<SysOrganEntity> listOrgan(String type);

}
