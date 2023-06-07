package ifmo.blss.tasks;


import ifmo.blss.entity.Message;
import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.User;
import ifmo.blss.exceptions.UserNotFoundException;
import ifmo.blss.repo.RecipeRepo;
import ifmo.blss.repo.UserRepo;

import ifmo.blss.service.Worker;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SendMail implements JavaDelegate {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    Worker worker;
//    @Autowired
//    ApproveRecipeJobFactory approveRecipeJobFactory;
    @Override

    public void execute(DelegateExecution execution) throws Exception {
        Long recipeId = Long.valueOf((Integer)execution.getVariable("selectedRecipeId"));
        Message message=makeMessageStatusСhangeRecipe(recipeId);
        worker.sendMail(message);


    }
    public Message makeMessageStatusСhangeRecipe(Long recipeId) throws UserNotFoundException {
        Recipe recipe=recipeRepo.findById(recipeId).get();
        User user = userRepo.findById(recipe.getAuthorId()).get();
        String subject = String.format("Смена статуса у рецепта: %s", recipe.getTitle());
        String emailAddress = user.getEmail();
        String text = String.format("Привет %s! Мы отправляем это письмо, " +
                "чтоб оповестить тебя о том, что твой рецепт %s был" +
                " проверен администратором и ему был установлен статус %s", user.getUsername(), recipe.getTitle(), recipe.getStatus(), new Date());
        return new Message(subject, emailAddress, text);
    }
//    public void ScheduleEmailSending(Message message) throws SchedulerException {
//
//        JobDetail emailSendJobDetail = createEmailSendJobDetail(message);
//        Trigger emailSendTrigger = createEmailSendJobTrigger(emailSendJobDetail);
//        scheduler.scheduleJob(emailSendJobDetail,emailSendTrigger);
//    }
}
