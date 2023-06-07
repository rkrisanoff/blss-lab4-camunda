package ifmo.blss.tasks;

import ifmo.blss.entity.Role;
import ifmo.blss.entity.User;
import ifmo.blss.repo.UserRepo;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SignUp implements JavaDelegate {
    @Autowired
    UserRepo userRepo;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");
        String email = (String) execution.getVariable("email");
        Boolean isAdmin = (Boolean) execution.getVariable("isAdmin");
        if (userRepo.existsByUsername(username)) {
            throw new BpmnError(
                    "signup_already_exists",
                    "User with username `%s` is already exists".formatted(username)
            );
        }
        if (userRepo.existsByEmail(email)) {
            throw new BpmnError(
                    "signup_already_exists",
                    "User with email `%s` is already exists".formatted(email)
            );
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(isAdmin ? Role.ADMIN : Role.USER);
        userRepo.save(user);
    }
}
