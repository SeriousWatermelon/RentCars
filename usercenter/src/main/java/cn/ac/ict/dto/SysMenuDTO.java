package cn.ac.ict.dto;

import cn.ac.ict.entity.SysMenuEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenuDTO extends SysMenuEntity {

    private String parentName;

    //子类菜单
    private List list;

}
