package com.offcn.project.vo;

import com.offcn.project.po.TReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRedisStorageVo {
    /** 项目的临时token */
    private String projectToken;
    /** 会员id */
    private Integer memberid;
    /** 项目的分类id */
    private List<Integer> typeids;
    /** 项目的标签id */
    private List<Integer> tagids;
    /** 项目名称 */
    private String name;
    /** 项目简介 */
    private String remark;
    /** 筹资金额 */
    private Integer money;
    /** 筹资天数 */
    private Integer day;
    /** 项目头部图片 */
    private String headerImage;
    /** 项目详情图片 */
    private List<String> detailsImage;
    /** 项目回报 */
    private List<TReturn> projectReturns;
}
