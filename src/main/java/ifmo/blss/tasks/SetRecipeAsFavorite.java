package ifmo.blss.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class SetRecipeAsFavorite implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }
}
