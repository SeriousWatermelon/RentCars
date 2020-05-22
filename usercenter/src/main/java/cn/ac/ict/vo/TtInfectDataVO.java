package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtInfectDataVO implements Serializable {

    private String city;

    private String cityCode;

    private String country;

    private Double lat;

    private Double lon;

    private String province;

}
