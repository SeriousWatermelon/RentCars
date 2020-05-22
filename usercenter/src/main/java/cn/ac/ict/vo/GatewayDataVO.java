package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayDataVO {

    private Date timestamp;

    private String type;

    private String mac;

    private Float gatewayFree;

    private Float gatewayLoad;

    private String bleName;

    private Integer rssi;

    private String rawData;

}
