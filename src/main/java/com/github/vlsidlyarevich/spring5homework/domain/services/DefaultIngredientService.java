package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.IngredientCommand;
import com.github.vlsidlyarevich.spring5homework.converters.IngredientCommandToIngredient;
import com.github.vlsidlyarevich.spring5homework.converters.IngredientToIngredientCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.RecipeReactiveRepository;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultIngredientService implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeRepository.findById(recipeId)
                .map(Recipe::getIngredients)
                .map(ingredients -> ingredients.stream()
                        .filter(ingredient -> ingredient.getId().equals(ingredientId))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(ingredient -> {
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient.get());
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {

        return Mono.empty();
//        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
//
//        if (!recipeOptional.isPresent()) {
//
//            //todo toss error if not found!
//            log.error("Recipe not found for id: " + command.getRecipeId());
//            return new IngredientCommand();
//        } else {
//            Recipe recipe = recipeOptional.get();
//
//            Optional<Ingredient> ingredientOptional = recipe
//                    .getIngredients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
//                    .findFirst();
//
//            if (ingredientOptional.isPresent()) {
//                Ingredient ingredientFound = ingredientOptional.get();
//                ingredientFound.setDescription(command.getDescription());
//                ingredientFound.setAmount(command.getAmount());
//                ingredientFound.setUom(unitOfMeasureRepository
//                        .findById(command.getUom().getId())
//                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
//            } else {
//                //add new Ingredient
//                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
//                //  ingredient.setRecipe(recipe);
//                recipe.addIngredient(ingredient);
//            }
//
//            Recipe savedRecipe = recipeRepository.save(recipe);
//
//            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
//                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
//                    .findFirst();
//
//            //check by description
//            if (!savedIngredientOptional.isPresent()) {
//                //not totally safe... But best guess
//                savedIngredientOptional = savedRecipe.getIngredients().stream()
//                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
//                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
//                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
//                        .findFirst();
//            }
//
//            //todo check for fail
//
//            //enhance with id value
//            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
//            ingredientCommandSaved.setRecipeId(recipe.getId());
//
//            return ingredientCommandSaved;
//        }

    }

    @Override
    public void deleteById(String recipeId, String idToDelete) {
//
//        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);
//
//        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
//
//        if (recipeOptional.isPresent()) {
//            Recipe recipe = recipeOptional.get();
//            log.debug("found recipe");
//
//            Optional<Ingredient> ingredientOptional = recipe
//                    .getIngredients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
//                    .findFirst();
//
//            if (ingredientOptional.isPresent()) {
//                log.debug("found Ingredient");
//                Ingredient ingredientToDelete = ingredientOptional.get();
//                // ingredientToDelete.setRecipe(null);
//                recipe.getIngredients().remove(ingredientOptional.get());
//                recipeRepository.save(recipe);
//            }
//        } else {
//            log.debug("Recipe Id Not found. Id:" + recipeId);
//        }
    }
}
