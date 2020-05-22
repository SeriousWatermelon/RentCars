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
@TableName("tt_contact_map")
public class TtContactMapEntity extends Model<TtContactMapEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 是否隔離。0未隔离，1已隔离
    private Integer quarantine;

    // 是否感染。0未感染，1感染
    private Integer infected;

    private String sourceId;

    private String targetId;

    private String userId;

    private String mac;

    private Date contactTime;

    private String baId;

    private Date groupContactTime;
}
