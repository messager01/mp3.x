package com.mp.crud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by Shipeixin on 2020/4/1 20:29
 */
@Data
@TableName("mp_user")
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    // 主键
    @TableId     // MP 默认主键为 id
    private Long UserId;

    // 姓名
    @TableField("name")
    private String realName;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

    //直属上级
    private Long managerId;

    //创建时间
    private LocalDateTime createTime;

    //  不代表任何意义  数据库中无对应
    @TableField(exist = false)  // 代表数据库中不存在
    private String remark;
}
