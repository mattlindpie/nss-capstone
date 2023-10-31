# Capstone Design Document

---

## 1. Problem Statement

---

People have to balance a lot of tasks when they cook. It's difficult for some to manually adjust recipes' ingredients to the amount they need versus the amount the recipe calls for. The goal of this app is to allow a user to build their own recipes and to provide them with the ability to easily adjust measurements according to the number of servings, switch between units of measurement, and create editable grocery lists based on the recipe's requirements.

## 2. Top Questions to Resolve in Review

---

1. Would it be a good idea to cache the recipes that are called from the database?
2. Does it make sense to store the ShoppingList items as a Map<Ingredient, Integer(quantity)> as opposed to 
just having a Set<String(ingredientNames)>

## 3. Use Cases

---

U1. As a customer, I want to create a new recipe

U2. As a customer, I want to retrieve a specific recipe

U3. As a customer, I want to be able to update a recipe with new values

U4. As a customer, I want to create a list of ingredients that I can delete individual items from.

U5. As a customer, I want variable units of measurement

U6. As a customer, I want to be able to change amount of ingredients based on how many servings I want

U7. As a customer, I want to sort recipes based on a certain ingredient

U8. As a customer, I want to schedule meals ahead of time

U9. As a customer, I want to pull recipes within a certain range of calories

U10. As a customer, I want to compare recipes

U11. As a customer, I want to save past lists

U12. As a customer, I want to have my favorite recipes displayed on the homepage

## 4. Project Scope

---

### 4.1 In Scope

- Create recipes and display their names on the homepage
- Display chosen recipe on its own page
    - Toggle between imperial and metric system
    - Allow for scaling ingredients according to number of servings
- Add recipe ingredients to a list
- Display list and quantities needed
- Delete items from list


### 4.2 Out of Scope

- Ability to compare recipes
- Scheduling meals ahead of time
- Favorite recipes
- Saving past lists
- Filtering recipes by individual ingredients
- Filtering recipes through a range of calories

## 5. Proposed Architecture Overview

---

This app will provide the minimum viable product including creating, retrieving, updating, and deleting recipes,
along with creating an editable shopping list, displaying all recipes on the homepage, and displaying a chosen
recipe with adjustable requirements according to number of desired servings, and with the ability to toggle
between systems of measurement.

Current expected endpoints(`CreateRecipe`, `GetRecipe`, `GetAllRecipes`, `UpdateRecipe`, `DeleteRecipe`,  `CreateList`, `GetList`, `UpdateList`)

Recipes and shopping lists will be stored in their respective DynamoDB tables. The homepage will display the 
names of all recipes along with the option to edit, delete, navigate to a details page, or add the recipe to 
the shopping list. The homepage will also have the option to create and save a new recipe to the table, and 
navigate to the shopping list page.

## 6. API

---

### 6.1 Public Models

#### RecipeModel

```
String userId
String recipeName
Integer servings
Map<Integer, String> recipeSteps
List<Ingredient> ingredients
Integer calories
Enum systemOfMeasurement
    - METRIC
    - IMPERIAL
```

#### IngredientModel

```
String ingredientName
Double amount
Enum unitOfMeasurement
    - OUNCES
    - FLUID_OUNCES
    - CUPS
    - TEASPOONS
    - TABLESPOONS
    - GRAMS
    - MILLIGRAMS
    - MILLILITERS
    - COUNT
```

#### ListModel

```
String userId
Map<Ingredient, Integer(quantity)>
```



### 6.2 CreateRecipe Endpoint

---

- Accepts `POST` requests to `/recipes`
- Accepts data to create a new recipe with a provided name, a given customerId, a number of servings,calories, a list of steps for the recipe, and a system of measurement

### 6.3 GetRecipe Endpoint

---

- Accepts `GET` requests to `/recipes/:recipeName`
- Accepts a user ID and a recipe name and returns the corresponding RecipeModel
    - if the recipe does not exist, it throws a `RecipeNotFoundException`

### 6.4 GetAllRecipes Endpoint

---

- Accepts `GET` requests to `/recipes`
- Accepts a userId and returns all recipes from table with matching id

### 6.5 UpdateRecipe Endpoint

---

- Accepts `PUT` requests to `/recipes/:recipeName`
- Accepts data to update a recipe such as new ingredients, changing their measurements, or removing them
- if the recipe does not exist, it throws a `RecipeNotFoundException`

### 6.6 DeleteRecipe Endpoint

---

- Accepts `DELETE` requests to `/recipes/:recipeName`
- Accepts a userId and recipeName to remove a recipe from the table
  - if the recipe does not exist, it throws a `RecipeNotFoundException`

### 6.7 CreateList Endpoint

---

- Accepts `POST` requests to `/list`
- Accepts a userId and creates a single list within the table
- Each user should only have one list

### 6.8 GetList Endpoint

---

- Accepts `GET` requests to `/list`
- Accepts a userId and retrieves the single list within the table

### 6.9 UpdateList Endpoint

---

- Accepts `PUT` requests to `/list`
- Accepts new lists of ingredients and adds them to existing list
- User will have the option to edit items from list or clear list

## 7. Tables

---

### Recipes
```
S // userId - hashkey
S // recipeName - rangekey
N // servings
Set<S> // steps
Set<S> // ingredients
N // calories
S // systemOfMeasurement
```

### List
```
S // userId - hashkey
Set<S> // listItems
```

## 8. Pages

---
