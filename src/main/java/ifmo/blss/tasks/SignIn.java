package ifmo.blss.tasks;

import ifmo.blss.entity.User;
import ifmo.blss.repo.UserRepo;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SignIn implements JavaDelegate{
    @Autowired
    UserRepo userRepo;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");
        if(!userRepo.existsByUsername(username)){
            throw new BpmnError(
                    "user_doesnt_exist",
                    "User with username %s doesn't exist".formatted(username)
            );
        }
        User user = userRepo.findByUsername(username);
        if (!user.getPassword().equals(password)){
            throw new BpmnError(
                    "unauthorized",
                    "Password doesn't correct"
            );
        }
        execution.setVariable("userId",user.getId());
        execution.setVariable("role",user.getRole().getName());
    }
}
