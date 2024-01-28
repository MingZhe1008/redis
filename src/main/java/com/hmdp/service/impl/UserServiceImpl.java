package com.hmdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import org.springframework.stereotype.Service;
import com.hmdp.utils.SystemConstants;
import javax.servlet.http.HttpSession;



/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * @param phone
     * @param session
     * @return
     */
    @Override
    public Result sendCode(String phone, HttpSession session) {
        // TODO 参数的判空校验
        // 校验手机号
        if(RegexUtils.isPhoneInvalid(phone)){
            log.error("手机号码格式错误");
            return Result.fail("手机号码格式错误");
        }
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 保存到session
        session.setAttribute("code",code);
        // 发送验证码
        log.debug("手机号为："+phone+"的手机号的验证码为："+code);
        return Result.ok();
    }

    /**
     * @param loginForm
     * @param session
     * @return
     */
    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // TODO 参数判空校验
        String phone = loginForm.getPhone();
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("手机格式错误");
        }
        // TODO 可以用redis 做一个登录次数限制的校验 3次  还有每日的校验次数 存一张表里
        // 校验验证码
        Object cacheCode = session.getAttribute("code");
        String code =loginForm.getCode();
        if(cacheCode == null || !cacheCode.equals(code)){
            log.error("验证码不一致");
            return Result.fail("验证码不一致");
        }
        User user = query().eq("phone", phone).one();
        if(user == null){
            // 创建新用户并且保存
            user  =  creatUserWithPhone(phone);
        }
        // 保存用户信息到Session中
        session.setAttribute("user",user);
        log.debug("手机号为"+user.getPhone()+"登陆成功");
        return Result.ok("登陆成功");
    }

    // 创建新用户
    private User creatUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX+RandomUtil.randomString(10));
        save(user);
        return user;
    }
}
