package com.lv.zupu.domain;

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
public class FamilyBo implements Serializable{
    private static final long serialVersionUID = -6904643942915445297L;
    private String familyName;//家族名称
    private Long id;//主键ID
    private Date createTime;//创建时间
    private String createUserId;//创建人ID
    private Date updateTime;//修改时间
    private String coverImgUrl;//封面图片
    private String describe;//简单描述
    private String authorName;//作者
    private Integer isDelete;
}
