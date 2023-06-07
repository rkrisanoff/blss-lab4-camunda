package ifmo.blss.tasks;

import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.Status;
import ifmo.blss.repo.RecipeRepo;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
public class GetRecipesOnModeration implements JavaDelegate {
    @Autowired
    RecipeRepo recipeRepo;

    @Override
    @Transactional
    public void execute(DelegateExecution execution) throws Exception {
        ArrayList<Recipe> recipes  = recipeRepo.findAllByStatus(Status.MODERATION);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("There are next id recipes:\n");
        recipes.forEach(recipe -> stringBuilder.append(recipe.getId()).append(", "));
        execution.setVariable("recipesOnModerationIdsRepresentation",stringBuilder.toString());
//        execution.setVariable("recipesOnModerationIds",recipes.stream().map(Recipe::getId).collect(Collectors.toCollection(ArrayList::new)));
    }
}
