package com.devil.controller;

import com.devil.entity.enums.ResponseCodeEnum;
import com.devil.entity.enums.VerifyRegexEnum;
import com.devil.entity.pojo.Userinfo;
import com.devil.entity.query.UserinfoQuery;
import com.devil.entity.vo.PaginationResultVO;
import com.devil.entity.vo.ReponseVo;
import com.devil.exception.BusinessException;
import com.devil.service.UserInfoService;
import com.devil.util.StringTools;
import com.devil.util.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName：UserInfoController
 *
 * @author: Devil
 * @Date: 2024/9/12
 * @Description:
 * @version: 1.0
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/saveUser")
    //@RequestBody 用来接收前端传递给后端的json字符串中的数据（接收数据）
    public ReponseVo saveUser(@RequestBody Userinfo userinfo){


        if (StringTools.isEmpty(userinfo.getPassword())){
            throw new BusinessException("密码不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"密码不能为空","error");
        }

        if(StringTools.isEmpty(userinfo.getUserName())){
            throw new BusinessException("用户名不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"用户名不能为空","error");
        }

        if(StringTools.isEmpty(userinfo.getPhone()) || !VerifyUtils.verify(VerifyRegexEnum.PHONE,userinfo.getPhone())){
            throw new BusinessException("手机号不能为空，且格式要正确");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"手机号不能为空，且格式要正确","error");
        }

        if(userinfo.getBirthday() == null){
            throw new BusinessException("出生日期不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"出生日期不能为空","error");
        }

        if(userinfo.getSex() == null){
            throw new BusinessException("性别不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"性别不能为空","error");
        }

        if(userinfo.getPosition() == null){
            throw new BusinessException("职位不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"职位不能为空","error");
        }

        if (StringTools.isEmpty(userinfo.getRoles())){
            throw new BusinessException("角色不能为空");
            //return ReponseVo.build(ResponseCodeEnum.CODE_600.getCode(),"角色不能为空","error");
        }

        //业务逻辑
        ReponseVo reponseVo = userInfoService.saveUserInfo(userinfo);

        return reponseVo;
    }

    //修改密码
    @PostMapping("/updatePassword")
    public ReponseVo updatePassword(@RequestParam("password") String password, @RequestParam("userId") Integer userId){
        //1.判空
        if(StringTools.isEmpty(password)){
            throw new BusinessException("密码不能为空");
        }

        if(userId == null){
            throw new BusinessException("用户id不能为空");
        }
        //2.判断用户名是否存在
        Userinfo userinfo = userInfoService.getUserinfoByUserId(userId);
        if(userinfo == null){
            throw new BusinessException("用户名不存在");
        }
        System.out.println("通过userid查出来的" + userinfo);
        userinfo.setPassword(password);
        System.out.println("设置了新密码的：" + userinfo);
        //3.修改密码
        ReponseVo reponseVo = userInfoService.updatePassword(userinfo);
        return reponseVo;
    }

    //删除
    //@PathVariable:映射 URL 请求中绑定的 占位符
    //http://localhost:9091/api/userInfo/delUser/10049
    @RequestMapping("/delUser/{userId}")
    public ReponseVo delUser (@PathVariable("userId") Integer userId){
        if(userId == null){
            throw new BusinessException("用户ID不存在");
        }
        Userinfo userinfo = userInfoService.getUserinfoByUserId(userId);
        if(userinfo == null){
            throw new BusinessException("用户不存在");
        }
        ReponseVo reponseVo = userInfoService.deleteUserinfoByUserId(userId);
        return reponseVo;
    }

    /**
     *
     * @param pageNum  页码，如果不传，默认是1
     * @param pageSize 每页显示数量
     * @return
     */
    //http://localhost:9091/api/userInfo/loadDataList/第几页/每页大小
    @RequestMapping(value = {"/loadDataList","/loadDataList/{pageNum}","/loadDataList/{pageNum}/{pageSize}"})
    public ReponseVo loadDataList(@PathVariable(value = "pageNum", required = false) Integer pageNum,
                                  @PathVariable(value = "pageSize",required = false) Integer pageSize){

        /*
        * 分页查询要返回什么东西？？？
        * 1.数据库中的数据
        * 2.总记录数
        * 3.总共有几页
        * 4.每页显示数量
        * 5.当前页码
        * */
         PaginationResultVO<Userinfo> userInfoServiceListPage =userInfoService.findListByPage(pageNum,pageSize);
        return ReponseVo.ok(userInfoServiceListPage);
    }


    /*
    * 根据条件分页查询
    * */
    @RequestMapping("/loadDataListOption")
    public ReponseVo loadDataListOption(@RequestBody(required = false) UserinfoQuery userinfoQuery){
        PaginationResultVO<Userinfo> userInfoServiceListPage =userInfoService.findListByPage(userinfoQuery);
        return ReponseVo.ok(userInfoServiceListPage);
    }


}
