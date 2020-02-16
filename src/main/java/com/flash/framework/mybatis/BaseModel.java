package com.flash.framework.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础Model
 *
 * @author zhurg
 * @date 2019/1/20 - 下午10:03
 */
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 4906588549386603600L;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    /**
     * 创建人
     */
    @TableField(value = "create_by", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, whereStrategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新人
     */
    @TableField(value = "update_by", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, whereStrategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 删除状态
     */
    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;
}