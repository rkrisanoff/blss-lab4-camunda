package ifmo.blss.tasks;

import ifmo.blss.DTO.SetApproveRecipeMessageDto;
import ifmo.blss.entity.Message;
import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.User;
import ifmo.blss.exceptions.UserNotFoundException;
import ifmo.blss.repo.RecipeRepo;
import ifmo.blss.repo.UserRepo;
import ifmo.blss.scheduling.ApproveRecipeJobFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class SendMail implements JavaDelegate {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    ApproveRecipeJobFactory approveRecipeJobFactory;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long recipeId = Long.valueOf((Integer)execution.getVariable("selectedRecipeId"));
        SetApproveRecipeMessageDto message=makeMessageStatusСhangeRecipe(recipeId);
        approveRecipeJobFactory.scheduleEmailSending(message);

    }
    private SetApproveRecipeMessageDto makeMessageStatusСhangeRecipe(Long recipeId) throws UserNotFoundException {
        Recipe recipe=recipeRepo.findById(recipeId).get();
        User user = userRepo.findById(recipe.getAuthorId()).get();
        String subject = String.format("Смена статуса у рецепта: %s", recipe.getTitle());
        String emailAddress = user.getEmail();
        String text = String.format("Привет %s! Мы отправляем это письмо, " +
                "чтоб оповестить тебя о том, что твой рецепт %s был" +
                " проверен администратором и ему был установлен статус %s", user.getUsername(), recipe.getTitle(), recipe.getStatus(), new Date());
        return new SetApproveRecipeMessageDto(subject, emailAddress, text);
    }
//    public void ScheduleEmailSending(Message message) throws SchedulerException {
//
//        JobDetail emailSendJobDetail = createEmailSendJobDetail(message);
//        Trigger emailSendTrigger = createEmailSendJobTrigger(emailSendJobDetail);
//        scheduler.scheduleJob(emailSendJobDetail,emailSendTrigger);
//    }
}
