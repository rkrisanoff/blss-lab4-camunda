package ifmo.blss.tasks;

import camundajar.impl.scala.Option;
import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.Status;
import ifmo.blss.repo.RecipeRepo;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class ApproveRecipe implements JavaDelegate {
    @Autowired
    RecipeRepo recipeRepo;
    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        Long recipeId = Long.valueOf((Integer)execution.getVariable("selectedRecipeId"));
        String newRecipeStatusRepresentation = (String) execution.getVariable("newRecipeStatus");
        Status newRecipeStatus = Status.valueOf(newRecipeStatusRepresentation);
        Optional<Recipe> maybeRecipe = recipeRepo.findById(recipeId);
        if (maybeRecipe.isEmpty()){
            throw new BpmnError(
                    "recipe_doesnt_exist",
                    "Recipe with id %d doesn't exist!".formatted(recipeId)
            );
        }
        maybeRecipe.get().setStatus(newRecipeStatus);
        recipeRepo.save(maybeRecipe.get());
        execution.setVariable(
                "notifyMessage",
                "The recipe with id %d is %s!".formatted(recipeId,newRecipeStatusRepresentation)
        );



    }
}
