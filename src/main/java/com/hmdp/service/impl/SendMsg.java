package com.hmdp.service.impl;

import cn.hutool.json.JSONObject;
import com.hmdp.dto.Result;

// 消息发送 四类消息类型
public class SendMsg {

    public Result sendMsg(String SendType , String smsType , String orderTime ,String nowTime){
        // todo 封装请求头


        switch (SendType){
            case "1":
                // APP
                break;
            case "2":
                // 微信
                break;
            case "3":
                // 短信
                break;
            case "4":
                // 邮件
                break;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sms",smsType);
        return null;
    }
}
