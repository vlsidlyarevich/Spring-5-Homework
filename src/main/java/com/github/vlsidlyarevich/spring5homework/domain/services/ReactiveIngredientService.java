package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.IngredientCommand;
import com.github.vlsidlyarevich.spring5homework.converters.IngredientCommandToIngredient;
import com.github.vlsidlyarevich.spring5homework.converters.IngredientToIngredientCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Ingredient;
import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.UnitOfMeasureRepository;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.RecipeReactiveRepository;
import com.github.vlsidlyarevich.spring5homework.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactiveIngredientService implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeReactiveRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        //TODO it smells
        return recipeReactiveRepository.findById(command.getRecipeId())
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Recipe not found for id: " + command.getRecipeId());
                    return Mono.error(new NotFoundException("Recipe not found for id: " + command.getRecipeId()));
                }))
                .map(recipe -> {
                    Optional<Ingredient> ingredientOptional = recipe
                            .getIngredients()
                            .stream()
                            .filter(ingredient -> ingredient.getId().equals(command.getId()))
                            .findFirst();

                    if (ingredientOptional.isPresent()) {
                        Ingredient ingredientFound = ingredientOptional.get();
                        ingredientFound.setDescription(command.getDescription());
                        ingredientFound.setAmount(command.getAmount());
                        ingredientFound.setUom(unitOfMeasureRepository
                                .findById(command.getUom().getId())
                                .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
                    } else {
                        //add new Ingredient
                        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                        //  ingredient.setRecipe(recipe);
                        recipe.addIngredient(ingredient);
                    }
                    return recipe;
                })
                .flatMap(recipeReactiveRepository::save)
                .map(recipe -> {
                    Optional<Ingredient> savedIngredientOptional = recipe.getIngredients().stream()
                            .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                            .findFirst();

                    //check by description
                    if (!savedIngredientOptional.isPresent()) {
                        //not totally safe... But best guess
                        savedIngredientOptional = recipe.getIngredients().stream()
                                .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                                .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                                .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                                .findFirst();
                    }

                    //todo check for fail

                    //enhance with id value
                    IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
                    ingredientCommandSaved.setRecipeId(recipe.getId());

                    return ingredientCommandSaved;
                });
    }


    @Override
    public void deleteById(String recipeId, String idToDelete) {
        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    if (recipe == null) {
                        log.debug("Recipe Id Not found. Id:" + recipeId);
                        return Mono.empty();
                    }

                    log.debug("found recipe");

                    Optional<Ingredient> ingredientOptional = recipe
                            .getIngredients()
                            .stream()
                            .filter(ingredient -> ingredient.getId().equals(idToDelete))
                            .findFirst();

                    if (ingredientOptional.isPresent()) {
                        log.debug("found Ingredient");
                        recipe.getIngredients().remove(ingredientOptional.get());
                    }

                    //TODO why it's not working if return dat
                    recipeReactiveRepository.save(recipe).block();
                    return recipe;
                }).log()
                .subscribe();
    }
}
