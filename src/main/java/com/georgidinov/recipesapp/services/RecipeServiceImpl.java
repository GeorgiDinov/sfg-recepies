package com.georgidinov.recipesapp.services;

import com.georgidinov.recipesapp.domain.Recipe;
import com.georgidinov.recipesapp.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    //== fields ==
    private final RecipeRepository recipeRepository;


    //== constructors ==
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }//end of constructor

    //== public methods ==
    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipesSet = new HashSet<>();
        this.recipeRepository.findAll().iterator().forEachRemaining(recipesSet::add);
        return recipesSet;
    }// end of method getRecipes

}//end of class RecipeServiceImpl
