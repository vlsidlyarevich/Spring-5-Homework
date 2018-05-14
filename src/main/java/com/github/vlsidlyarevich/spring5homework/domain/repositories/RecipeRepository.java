package com.github.vlsidlyarevich.spring5homework.domain.repositories;

import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
