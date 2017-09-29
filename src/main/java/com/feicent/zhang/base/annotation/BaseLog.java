package com.feicent.zhang.base.annotation;

import java.util.Date;

/**
 * 基础日志
 * 注释的使用:在类中使用自定义注解。
 */
@MyTable(name="T_BaseLog",version="2")
public class BaseLog{
    
    @MyField(name="addTime",type="Date")
    private Date log_time; // 时间

    @MyField(name="log_level",type="String")
    private String log_level; // 级别

    @MyField(name="message",type="String")
    private String message; // 日志内容

    public Date getLog_time()
    {
        return log_time;
    }

    public void setLog_time(Date log_time)
    {
        this.log_time = log_time;
    }

    public String getLog_level()
    {
        return log_level;
    }

    public void setLog_level(String log_level)
    {
        this.log_level = log_level;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
