package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.RecipeCommand;
import com.github.vlsidlyarevich.spring5homework.converters.RecipeCommandToRecipe;
import com.github.vlsidlyarevich.spring5homework.converters.RecipeToRecipeCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Recipe;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.RecipeRepository;
import com.github.vlsidlyarevich.spring5homework.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultRecipeService implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(String id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe Not Found. For ID value: " + id);
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));

        //enhance command object with id value
        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
        }

        return recipeCommand;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }
}