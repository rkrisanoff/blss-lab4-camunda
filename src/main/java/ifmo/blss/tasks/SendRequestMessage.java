package ifmo.blss.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class SendRequestMessage implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("approve-recipe-notify")
                .setVariable("subject",execution.getVariable("subject"))
                .setVariable("title",execution.getVariable("title"))
                .setVariable("text",execution.getVariable("text"))
                .correlateStartMessage();

    }
}
