package ifmo.blss.scheduling;


import ifmo.blss.DTO.DeleteFavoriteRecipeMessageDto;
import ifmo.blss.DTO.SetFavoriteRecipeMessageDto;
import ifmo.blss.jobs.EmailSendJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

@Component
public class FavoriteRecipeNotifyJobFactory {

    @Autowired
    Scheduler scheduler;

    @Autowired
    private ApplicationContext applicationContext;
    private JobDetail createFavoriteRecipeNofityJobDetail(SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto) {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(EmailSendJob.class);
        factory.setDescription("Email Sender");
        factory.setDurability(true);
        factory.setApplicationContext(applicationContext);
        factory.setName(setFavoriteRecipeMessageDto.getEmail());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("emailTo", setFavoriteRecipeMessageDto.getEmail());
        jobDataMap.put("subject", setFavoriteRecipeMessageDto.getTitle());
        jobDataMap.put("text", setFavoriteRecipeMessageDto.getText());
        factory.setJobDataMap(jobDataMap);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Trigger createFavoriteRecipeNotifyJobTrigger(JobDetail emailSendJobDetail) throws ParseException {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(emailSendJobDetail);
        factory.setStartTime(Date.from(Instant.now().plus(10, ChronoUnit. SECONDS)));
        factory.setCronExpression("0 * * * * ?");
        factory.setName((String) emailSendJobDetail.getJobDataMap().get("emailTo"));
        factory.afterPropertiesSet();
        return factory.getObject();
    }


    public void scheduleFavoriteRecipeNotifying(SetFavoriteRecipeMessageDto setFavoriteRecipeMessageDto) throws SchedulerException, ParseException {
        JobDetail favoriteRecipeNotifyJobDetail = createFavoriteRecipeNofityJobDetail(setFavoriteRecipeMessageDto);
        Trigger favoriteRecipeNotifyTrigger = createFavoriteRecipeNotifyJobTrigger(favoriteRecipeNotifyJobDetail);
        scheduler.scheduleJob(favoriteRecipeNotifyJobDetail, Set.of(favoriteRecipeNotifyTrigger),true);
    }


    public void deleteScheduledFavoriteRecipeNotifying(DeleteFavoriteRecipeMessageDto deleteFavoriteRecipeMessageDto) throws SchedulerException {
        JobKey jobKey = new JobKey(deleteFavoriteRecipeMessageDto.getEmail());
        scheduler.deleteJob(jobKey);
    }

}
