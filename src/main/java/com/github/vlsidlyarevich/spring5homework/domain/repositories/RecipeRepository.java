package com.github.vlsidlyarevich.spring5homework.domain.repositories;

import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
