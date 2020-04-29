package com.flash.framework.mybatis.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flash.framework.mybatis.BaseModel;
import lombok.Data;

/**
 * @author zhurg
 * @date 2020/4/28 - 9:58 PM
 */
@Data
@TableName("user")
public class User extends BaseModel {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String email;
}