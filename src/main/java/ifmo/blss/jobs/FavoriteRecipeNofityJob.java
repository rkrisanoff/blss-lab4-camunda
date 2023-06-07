package ifmo.blss.jobs;

//import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
//import com.mailSender.MailSender.service.interfaces.SendEmail;
import ifmo.blss.DTO.SetApproveRecipeMessageDto;
import ifmo.blss.entity.Message;
import ifmo.blss.service.Worker;
import ifmo.blss.tasks.SendMail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class FavoriteRecipeNofityJob implements Job {
    @Autowired
    Worker emailSender;

    @Override
    public void execute(JobExecutionContext context)  {
        try {
            emailSender.sendMail(new Message(
                    (String) context.getJobDetail().getJobDataMap().get("subject"),
                    (String) context.getJobDetail().getJobDataMap().get("emailTo"),
                    (String) context.getJobDetail().getJobDataMap().get("text")
            ));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
