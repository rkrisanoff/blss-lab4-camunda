package ifmo.blss.repo;

import ifmo.blss.entity.Recipe;
import ifmo.blss.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface RecipeRepo extends PagingAndSortingRepository<Recipe, Long> {
    Recipe save(Recipe recipe);

//    Page<Recipe> findAllByStatus(Status status, Pageable pageable);
    ArrayList<Recipe> findAllByStatus(Status status);

    @Modifying
    @Query("update Recipe recipe set recipe.status = ?1 where recipe.id = ?2")
    int setStatusForRecipe(Status status, Long id);

}
