package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    void deleteById(String recipeId, String idToDelete);
}
