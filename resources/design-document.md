# Capstone Design Document

---

## 1. Problem Statement

---

People have to balance a lot of tasks when they cook. It's difficult for some to manually adjust recipes' ingredients to the amount they need versus the amount the recipe calls for. The goal of this app is to allow a user to build their own recipes and to provide them with the ability to easily adjust measurements according to the number of servings, switch between units of measurement, and create editable grocery lists based on the recipe's requirements.

## 2. Use Cases

---

U1. As a customer, I want to create a new recipe

U2. As a customer, I want to retrieve a specific recipe

U3. As a customer, I want to be able to update a recipe with new values

U4. As a customer, I want to create a shopping list, with ingredients from chosen recipes, that I can delete individual items from.

U5. As a customer, I want to be able to select different units of measurement for a specific ingredient (or recipe)

U6. As a customer, I want to be able to change amount of ingredients based on how many servings I want to make of a given recipe

U7. As a customer, I want to filter recipes based on a certain ingredient

U8. As a customer, I want to schedule meals ahead of time

U9. As a customer, I want to pull recipes within a certain range of calories

U10. As a customer, I want to compare recipes to see differences in ingredients and quantities

U11. As a customer, I want to save past lists

U12. As a customer, I want to have my favorite recipes displayed on the homepage

## 3. Project Scope

---

### 3.1 In Scope

- Create recipes and display their names on the homepage
- Display chosen recipe on its own page
    - Allow for scaling ingredients according to number of servings
- Add recipe ingredients to a shopping list
- Display list and quantities needed
- Filtering recipes through a range of calories
- Delete items from list


### 3.2 Out of Scope

- Ability to compare recipes
- Scheduling meals ahead of time
- Favorite recipes
- Saving past lists
- Filtering recipes by individual ingredients

## 4. Proposed Architecture Overview

---

This app will provide the minimum viable product including creating, retrieving, updating, and deleting recipes,
along with creating an editable shopping list, displaying all recipes on the homepage, and displaying a chosen
recipe with adjustable requirements according to number of desired servings, and with the ability to toggle
between systems of measurement.

Current expected endpoints(`CreateRecipe`, `GetRecipe`, `GetAllRecipes`, `UpdateRecipe`, 
`DeleteRecipe`,  `AddToShoppingList`, `GetShoppingList`, `UpdateShoppingList`)

Recipes and shopping lists will be stored in their respective DynamoDB tables. The homepage will display the 
names of all recipes along with the option to edit, delete, navigate to a details page, or add the recipe to 
the shopping list. The homepage will also have the option to create and save a new recipe to the table, and 
navigate to the shopping list page.

## 5. API

---

### 5.1 Public Models

#### RecipeModel

```
String userId
String recipeName
Integer servings
List<String> recipeSteps
List<Ingredient> ingredients
Integer calories
```

#### IngredientModel

```
String ingredientName
Double amount
String unitOfMeasurement

```

#### ShoppingListModel

```
String userId
Map<Ingredient, Integer(quantity)> shoppingListItems
```



### 5.2 CreateRecipe Endpoint

---

- Accepts `POST` requests to `/recipes`
- Accepts data to create a new recipe with a provided name, a given customerId, a number of servings,calories, a list of steps for the recipe, and a system of measurement

### 5.3 GetRecipe Endpoint

---

- Accepts `GET` requests to `/recipes/:recipeName`
- Accepts a user ID and a recipe name and returns the corresponding RecipeModel
    - if the recipe does not exist, it throws a `RecipeNotFoundException`

### 5.4 GetAllRecipes Endpoint

---

- Accepts `GET` requests to `/recipes`
- Accepts a userId and returns all recipes from table with matching id

### 5.5 UpdateRecipe Endpoint

---

- Accepts `PUT` requests to `/recipes/{recipeName}`
- Accepts data to update a recipe such as new ingredients, changing their measurements, or removing them
- if the recipe does not exist, it throws a `RecipeNotFoundException`

### 5.6 DeleteRecipe Endpoint

---

- Accepts `DELETE` requests to `/recipes/{recipeName}`
- Accepts a userId and recipeName to remove a recipe from the table
  - if the recipe does not exist, it throws a `RecipeNotFoundException`

### 5.7 AddToShoppingList Endpoint

---

- Accepts `POST` requests to `/shoppingList`
- Accepts a userId and adds ingredients to the table
- If no shopping list for the user exists, it will create one
- Each user should only have one list

### 5.8 GetList Endpoint

---

- Accepts `GET` requests to `/shoppingList`
- Accepts a userId and retrieves the single list within the table

### 5.9 UpdateList Endpoint

---

- Accepts `PUT` requests to `/shoppingList`
- Accepts new lists of ingredients and adds them to existing list
- User will have the option to edit items from list or clear list

## 6. Tables

---

### Recipes
```
S // userId - hashkey/GSIHashkey
S // recipeName - rangekey
N // servings
S // steps
S // ingredients
N // calories/GSIRangekey
```

### ShoppingList
```
S // userId - hashkey
S // shoppingListItems
```

