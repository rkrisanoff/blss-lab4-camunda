package ifmo.blss.tasks;

import ifmo.blss.entity.Kitchen;
import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.Status;
import ifmo.blss.repo.RecipeRepo;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddRecipe implements JavaDelegate {
    @Autowired
    RecipeRepo recipeRepo;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String title = (String) execution.getVariable("title");
        String kitchenName = (String) execution.getVariable("kitchen");
        String description = (String) execution.getVariable("description");
        try {
            Kitchen kitchen = Kitchen.valueOf(kitchenName.toUpperCase());
            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setStatus(Status.MODERATION);
            recipe.setDescription(description);
            recipe.setKitchen(kitchen);
            recipe.setAuthorId((Long) execution.getVariable("userId"));
            recipeRepo.save(recipe);
            execution.setVariable(
                    "notifyMessage",
                    "Рецепт %s успешно создан!".formatted(title)
            );
        } catch (IllegalArgumentException e){
            System.out.println(e.toString());
            throw new BpmnError("wrong_kitchen");
        }

    }
}
