package ifmo.blss.tasks;

import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.Status;
import ifmo.blss.repo.RecipeRepo;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetRecipeOnModerationById implements JavaDelegate {

    @Autowired
    RecipeRepo recipeRepo;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long selectedRecipeId = Long.valueOf((Integer) execution.getVariable("selectedRecipeId"));
        Optional<Recipe> recipe = recipeRepo.findById(selectedRecipeId);
        if (recipe.isEmpty()){
            throw new BpmnError(
                    "recipe_doesnt_exist",
                    "Recipe with id %d doesn't exist!".formatted(selectedRecipeId)
            );
        }
        if (recipe.get().getStatus().equals(Status.MODERATION)){
            throw new BpmnError(
                    "recipe_doesnt_on_moderation",
                    "Recipe with id %d doesn't on moderation!".formatted(selectedRecipeId)
            );
        }
        execution.setVariable("selectedRecipeAuthorId", recipe.get().getAuthorId().toString());
        execution.setVariable("selectedRecipeTitle",recipe.get().getTitle());
        execution.setVariable("selectedRecipeStatus",recipe.get().getStatus().getName());
        execution.setVariable("selectedRecipeKitchen",recipe.get().getKitchen().getName());
        execution.setVariable("selectedRecipeDescription",recipe.get().getDescription());
    }
}
