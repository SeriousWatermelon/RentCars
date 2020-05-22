package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_infect_data")
public class TtInfectDataEntity extends Model<TtInfectDataEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String city;

    private String country;

    private String latitude;

    private String longitude;

    private String province;

    private Date createTime;

}
