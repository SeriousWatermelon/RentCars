package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysOrganVO implements Serializable {

    private String baId;

    private String name;

}
