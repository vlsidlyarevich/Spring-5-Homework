package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.RecipeCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    void deleteById(String idToDelete);
}
