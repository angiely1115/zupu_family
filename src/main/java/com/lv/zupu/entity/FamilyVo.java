package com.lv.zupu.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvrongzhuan
 * @Description: 家族实体
 * @Date: 2018/7/1 14:45
 * @Version: 1.0
 * modified by:
 */
@Data
@TableName(value = "zupu_family")
public class FamilyVo extends Model<FamilyVo>{
    private static final long serialVersionUID = 2918594741142107392L;
     @TableField
     @NotBlank(message = "名称不能空")
    private String familyName;//家族名称
    @TableId
    private Long id;//主键ID
    @TableField
    private Date createTime;//创建时间
    @TableField
    private String createUserId;//创建人ID
    @TableField(update="now()")
    private Date updateTime;//修改时间
    @TableField
    private String coverImgUrl;//封面图片
    @TableField
    private String describe;//简单描述
    @TableField
    private String authorName;//作者
    @TableField
    @TableLogic//逻辑删除
    private Integer isDelete;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
