package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtInfectMacVO implements Serializable {

    private String time;

    private String macAddress;

    private String rssi;

}
