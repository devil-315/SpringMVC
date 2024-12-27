package com.devil.service.impl;

import com.devil.dao.UserInfoDao;
import com.devil.entity.enums.PageSize;

import com.devil.entity.pojo.Userinfo;

import com.devil.entity.query.SimplePage;
import com.devil.entity.query.UserinfoQuery;
import com.devil.entity.vo.PaginationResultVO;
import com.devil.entity.vo.ReponseVo;
import com.devil.exception.BusinessException;
import com.devil.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName：UserInfoServiceImpl
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    //新增或者修改
    @Override
    @Transactional(rollbackFor = Exception.class) //使用事务 , 配置回滚类型Exception.class
    public ReponseVo saveUserInfo(Userinfo userinfo) {

        /*
         * TODO 假设 数据中已经存在 某个（188xxx）手机号，你要修改这条数据的名字，因为先查询的是手机号是否重复，
         *  就会先查到已经有一个（188xxx），
         *  此时直接报重复，无法修改。
         * */

        int num = 0;


        //新增
         if(userinfo.getUserId() == null){
             userinfo.setCreateTime(new Date());

             num = userInfoDao.insert(userinfo);
         }else {
             //修改
             //TODO 不允许修改密码
             //TODO 设置成自己原本的密码
             /*userinfo.setPassword(null);
             userinfo.setCreateTime(null);*/

             num = userInfoDao.updateByUserId(userinfo);
         }

        //手机号不能重复
        Userinfo userInfoQuery = new Userinfo();
        userInfoQuery.setPhone(userinfo.getPhone());
        Integer count = userInfoDao.selectCount(userinfo);
        if(count > 1){
            throw new BusinessException("手机号已重复");
            //return ReponseVo.build(ResponseCodeEnum.CODE_601.getCode(),"手机号已重复","error");
        }
        if(num>0){
            return ReponseVo.ok();
        }

        return ReponseVo.fail();
    }

    @Override
    public Userinfo getUserinfoByUserId(Integer userId) {
        return userInfoDao.selectByUserId(userId);
    }

    @Override
    public ReponseVo updatePassword(Userinfo userinfo) {
        Integer num = userInfoDao.updatePassword(userinfo);
        if(num > 0){
            return ReponseVo.ok();
        }
        throw new BusinessException("修改失败");
    }

    @Override
    public ReponseVo deleteUserinfoByUserId(Integer userId) {
        Integer num = userInfoDao.deleteUserinfoByUserId(userId);
        if(num > 0){
            return ReponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVO<Userinfo> findListByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0||pageNum < 0){
            pageNum = 1;
        }
        if (pageSize == null|| pageSize <= 0){
            pageSize = 5;
        }
        /*
        * 假设每页显示数量是 5
        * 第一页 0，5
        * 第二页 5，5
        * 第三页 10，5
        * */

        Integer pageStart = (pageNum - 1) * pageSize;

        //总记录数
        int count = findCountByParam();

        //总页数 已知 总记录数（14）和每页显示数量（5），计算总页数（3）
        //int allTotal =count /pageSize == 0 ? (count /pageSize ):(count /pageSize + 1);
        int allTotal = (int)Math.ceil((double) count /pageSize);

        //select * from user_info limit pageStart,pageSize
        //数据
        List<Userinfo> result =userInfoDao.getAllUser(pageStart,pageSize);

        return   new PaginationResultVO<Userinfo>(count, pageSize, pageNum, allTotal, result);
    }

    @Override
    public PaginationResultVO<Userinfo> findListByPage(UserinfoQuery userinfoQuery) {
        //查询总记录数
        //TODO 异常
        int count = findCountByParam();

        //判断 传入的每页显示数量是否为空，如果不是就为自己，否则就是5
        int pageSize = userinfoQuery.getPageSize() == null ? PageSize.SIZE5.getSize() :userinfoQuery.getPageSize();

        SimplePage simplePage = new SimplePage(userinfoQuery.getPageNo(), count, pageSize);
        userinfoQuery.setSimplePage(simplePage);

        //根据条件查询数据
       List<Userinfo> userInfo = selectListByParam(userinfoQuery);


        PaginationResultVO<Userinfo> resultVO =
                new PaginationResultVO<>(count, simplePage.getPageSize(),
                        simplePage.getPageNo(), simplePage.getPageTotal(), userInfo);
        return resultVO;
    }

    private List<Userinfo> selectListByParam(UserinfoQuery userinfoQuery) {
        return userInfoDao.selectListByParam(userinfoQuery);
    }

    private int findCountByParam(){
        //select count(*) from user_info
        return userInfoDao.selectCounts();
    }


}
