package main.najah.test;
import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

@DisplayName("RecipeBook Tests")
public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe recipe;

    @BeforeEach
    void setUp() throws RecipeException {
        recipeBook = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Mocha");
        recipe.setPrice("25");
        recipe.setAmtCoffee("2");
        recipe.setAmtMilk("1");
        recipe.setAmtSugar("1");
        recipe.setAmtChocolate("1");
        System.out.println("--> Setup complete for RecipeBook test.");
    }

    @Test
    @DisplayName("Add Valid Recipe")
    void testAddRecipe() {
        boolean added = recipeBook.addRecipe(recipe);
        assertTrue(added, "Recipe should be added successfully");
    }

    @Test
    @DisplayName("Don't Add Duplicate Recipe")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe);
        boolean addedAgain = recipeBook.addRecipe(recipe);
        assertFalse(addedAgain, "Duplicate recipe should not be added");
    }

    @Test
    @DisplayName("Edit Existing Recipe")
    void testEditRecipe() throws RecipeException {
        recipeBook.addRecipe(recipe);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("Latte");
        newRecipe.setPrice("30");
        newRecipe.setAmtCoffee("2");
        newRecipe.setAmtMilk("2");
        newRecipe.setAmtSugar("2");
        newRecipe.setAmtChocolate("0");

        String oldName = recipeBook.editRecipe(0, newRecipe);
        assertEquals("Mocha", oldName, "Recipe name should match the old name");
    }

    @Test
    @DisplayName("Edit Non-Existent Recipe")
    void testEditNonExistingRecipe() throws RecipeException {
        Recipe dummy = new Recipe();
        dummy.setName("Fake");
        String result = recipeBook.editRecipe(2, dummy);
        assertNull(result, "Editing non-existent recipe should return null");
    }

    @Test
    @DisplayName("Delete Existing Recipe")
    void testDeleteRecipe() {
        recipeBook.addRecipe(recipe);
        String deletedName = recipeBook.deleteRecipe(0);
        assertEquals("Mocha", deletedName, "Deleted recipe name should match");
    }

    @Test
    @DisplayName("Delete Non-Existent Recipe")
    void testDeleteNonExistingRecipe() {
        String deleted = recipeBook.deleteRecipe(3);
        assertNull(deleted, "Deleting non-existent recipe should return null");
    }

    @Test
    @DisplayName("Test Get Recipes Not Null")
    void testGetRecipes() {
        assertNotNull(recipeBook.getRecipes(), "Recipes array should not be null");
    }

    @Test
    @DisplayName("Timeout Test for Adding Recipe")
    void testAddRecipeTimeout() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            recipeBook.addRecipe(recipe);
        });
    }

    @Test
    @DisplayName("Multiple Assertions on Recipe Data")
    void testRecipeData() throws RecipeException {
        recipeBook.addRecipe(recipe);
        Recipe[] recipes = recipeBook.getRecipes();

        assertAll(
                () -> assertNotNull(recipes[0]),
                () -> assertEquals("Mocha", recipes[0].getName()),
                () -> assertEquals(25, recipes[0].getPrice()),
                () -> assertTrue(recipes.length > 0)
        );
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"Espresso", "Latte", "Cappuccino"})
    @DisplayName("Parameterized Test for Adding Different Recipes")
    void testAddMultipleRecipes(String name) throws RecipeException {
        Recipe r = new Recipe();
        r.setName(name);
        r.setPrice("10");
        r.setAmtCoffee("1");
        r.setAmtMilk("1");
        r.setAmtSugar("1");
        r.setAmtChocolate("1");

        boolean added = recipeBook.addRecipe(r);
        assertTrue(added);
    }
}