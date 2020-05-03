package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.RecipeCommand;
import com.github.vlsidlyarevich.spring5homework.converters.RecipeCommandToRecipe;
import com.github.vlsidlyarevich.spring5homework.converters.RecipeToRecipeCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.RecipeReactiveRepository;
import com.github.vlsidlyarevich.spring5homework.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactiveRecipeService implements RecipeService {

    private final RecipeReactiveRepository reactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Flux<Recipe> getRecipes() {
        return reactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return reactiveRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Recipe Not Found. For ID value: " + id)));
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> findCommandById(String id) {
        return reactiveRepository.findById(id)
                .map(recipeToRecipeCommand::convert)
                .map(recipeCommand -> {
                    //enhance command object with id value
                    if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
                        recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));
                    }

                    return recipeCommand;
                });
    }

    @Override
    @Transactional
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        return reactiveRepository.save(detachedRecipe)
                .doOnNext(savedRecipe -> log.debug("Saved RecipeId:" + savedRecipe.getId()))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public void deleteById(String idToDelete) {
        reactiveRepository.deleteById(idToDelete).subscribe();
    }
}
