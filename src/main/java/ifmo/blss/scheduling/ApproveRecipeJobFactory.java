package ifmo.blss.scheduling;

import ifmo.blss.DTO.SetApproveRecipeMessageDto;
import ifmo.blss.jobs.EmailSendJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;

@Component
public class ApproveRecipeJobFactory {
    @Autowired
    private ApplicationContext applicationContext;
    private JobDetail createEmailSendJobDetail(SetApproveRecipeMessageDto setApproveRecipeMessageDto) {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(EmailSendJob.class);
        factory.setDescription("Email Sender");
        factory.setDurability(true);
        factory.setApplicationContext(applicationContext);
        factory.setName(setApproveRecipeMessageDto.getEmailTo());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("emailTo", setApproveRecipeMessageDto.getEmailTo());
        jobDataMap.put("subject", setApproveRecipeMessageDto.getSubject());
        jobDataMap.put("text", setApproveRecipeMessageDto.getText());
        factory.setJobDataMap(jobDataMap);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    private Trigger createEmailSendJobTrigger(JobDetail emailSendJobDetail)  {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(emailSendJobDetail);
        // TODO change to interval, corresponding to actual business-process requirements
        factory.setStartTime(Date.from(Instant.now().plus(30, ChronoUnit. SECONDS)));
        factory.setRepeatCount(0);
        factory.setRepeatInterval(100);
        factory.setName("Email Sender Trigger");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Autowired
    Scheduler scheduler;

    public void scheduleEmailSending(SetApproveRecipeMessageDto setApproveRecipeMessageDto) throws SchedulerException {
        JobDetail emailSendJobDetail = createEmailSendJobDetail(setApproveRecipeMessageDto);
        Trigger emailSendTrigger = createEmailSendJobTrigger(emailSendJobDetail);
        scheduler.scheduleJob(emailSendJobDetail, Set.of(emailSendTrigger),true  );
    }
}
