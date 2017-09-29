package com.feicent.zhang.util.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import org.springframework.beans.factory.annotation.Autowired;

import com.feicent.zhang.util.scheduler.entity.ScheduleJob;

public class SchedulerUtil {
	
	@Autowired
	private Scheduler scheduler;
	//@Autowired
	//private SchedulerFactoryBean schedulerFactoryBean;
	
	/**
	 * 计划中的任务 
	 * @throws SchedulerException
	 */
	public void jobList() throws SchedulerException{
	    //Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
	    Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
	    List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
	    for (JobKey jobKey : jobKeys) {
	        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
	        for (Trigger trigger : triggers) {
	            ScheduleJob job = new ScheduleJob();
	            job.setJobName(jobKey.getName());
	            job.setJobGroup(jobKey.getGroup());
	            job.setDescription("触发器:" + trigger.getKey());
	            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
	            job.setJobStatus(triggerState.name());
	            if (trigger instanceof CronTrigger) {
	                CronTrigger cronTrigger = (CronTrigger) trigger;
	                String cronExpression = cronTrigger.getCronExpression();
	                job.setCronExpression(cronExpression);
	            }
	            jobList.add(job);
	        }
	    }
	}
	
	/**
	 * 运行中的任务
	 * @throws SchedulerException
	 */
	public void runningJobs() throws SchedulerException{
	    List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
	    List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
	    for (JobExecutionContext executingJob : executingJobs) {
	        ScheduleJob job = new ScheduleJob();
	        JobDetail jobDetail = executingJob.getJobDetail();
	        JobKey jobKey = jobDetail.getKey();
	        Trigger trigger = executingJob.getTrigger();
	        job.setJobName(jobKey.getName());
	        job.setJobGroup(jobKey.getGroup());
	        job.setDescription("触发器:" + trigger.getKey());
	        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
	        job.setJobStatus(triggerState.name());
	        if (trigger instanceof CronTrigger) {
	            CronTrigger cronTrigger = (CronTrigger) trigger;
	            String cronExpression = cronTrigger.getCronExpression();
	            job.setCronExpression(cronExpression);
	        }
	        jobList.add(job);
	    }
	}
	
	/**
	 * 暂停任务 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException{
	    JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
	    scheduler.pauseJob(jobKey);
	}
	
	/**
	 * 恢复任务 
	 * @param scheduleJob
	 * @throws SchedulerException 
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 删除任务 
	 * 删除任务后，所对应的trigger也将被删除 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
	
	/**
	 * 立即运行任务
	 * 只会运行一次，方便测试时用
	 * quartz是通过临时生成一个trigger的方式来实现的，这个trigger将在本次任务运行完成之后自动删除。
	 * trigger的key是随机生成的，例如：DEFAULT.MT_4k9fd10jcn9mg。在我的测试中，前面的DEFAULT.MT是固定的，后面部分才随机生成。 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void triggerJob(ScheduleJob scheduleJob) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 更新任务的时间表达式 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJob(ScheduleJob scheduleJob) throws SchedulerException{
	    TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(),
	        scheduleJob.getJobGroup());
	    //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
	    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	    //表达式调度构建器
	    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob
	        .getCronExpression());
	    //按新的cronExpression表达式重新构建trigger
	    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
	        .withSchedule(scheduleBuilder).build();
	    //按新的trigger重新设置job执行
	    scheduler.rescheduleJob(triggerKey, trigger);
	}
	
}
