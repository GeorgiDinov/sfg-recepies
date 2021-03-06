package com.georgidinov.recipesapp.controllers;

import com.georgidinov.recipesapp.commands.RecipeCommand;
import com.georgidinov.recipesapp.exceptions.NotFoundException;
import com.georgidinov.recipesapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    //== constants ==
    private static final String RECIPE_RECIPE_FORM_URL = "recipes/recipeform";


    //== fields ==
    private final RecipeService recipeService;

    //== constructors ==
    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    //== public methods ==
    @GetMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));
        return "recipes/show";
    }//end of method recipes

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPE_FORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_RECIPE_FORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
                               BindingResult result) {
        if (result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPE_FORM_URL;
        }
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        String toRequestMapping = "/recipe/" + savedCommand.getId() + "/show";
        return "redirect:" + toRequestMapping;
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id {}", id);
        this.recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    //== exception handlers ==
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }




}//end of class RecipeController
