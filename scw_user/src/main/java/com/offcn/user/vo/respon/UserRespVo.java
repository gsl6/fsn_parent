package com.offcn.user.vo.respon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespVo {
    @ApiModelProperty("访问令牌，请妥善保管，以后每次请求都要带上")
    /** 访问令牌 */
    private String accessToken;
    /** 存储手机号 */
    private String loginacct;
    private String username;
    private String email;
    private String authstatus;
    private String usertype;
    private String realname;
    private String cardnum;
    private String accttype;
}
