package com.devil.service;

import com.devil.entity.pojo.Userinfo;
import com.devil.entity.query.UserinfoQuery;
import com.devil.entity.vo.PaginationResultVO;
import com.devil.entity.vo.ReponseVo;

/**
 * ClassName：UserInfoService
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
/*
* 用户信息 业务接口
* */
public interface UserInfoService {
    ReponseVo saveUserInfo(Userinfo userinfo);

    Userinfo getUserinfoByUserId(Integer userId);

    ReponseVo updatePassword(Userinfo userinfo);

    ReponseVo deleteUserinfoByUserId(Integer userId);

    PaginationResultVO<Userinfo> findListByPage(Integer pageNum, Integer pageSize);

    PaginationResultVO<Userinfo> findListByPage(UserinfoQuery userinfoQuery);
}
