package com.feicent.zhang.util.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feicent.zhang.util.scheduler.entity.ScheduleJob;

/**
 * 定时任务运行主入口
 * @author admin
 * Quartz设计的核心类包括 Scheduler, Job 以及 Trigger
 * 其中，Job 负责定义需要执行的任务，Trigger 负责设置调度策略
 * Scheduler 将二者组装在一起，并触发任务开始执行
 */
public class QuartzJobFactory implements Job {

	private String JOBDETAIL_KEY  = "";
	private static Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("************开始执行任务调度***********");
		try {
			//获取任务调度信息
	        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get(JOBDETAIL_KEY);
	        if(scheduleJob!=null){
        		//执行定时任务
	        	//executeJob();
	        }else{
	        	logger.error("任务调度对象为空!!!");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
