# Portion Perfect
---
## 1. Problem Statement

---

People have to balance a lot of tasks when they cook. It's difficult for some to navigate recipe sites or get through complex layouts of certain recipe applications. 
The goal of this app is to allow a user to build their own recipes, provide them with the ability to easily adjust them according to their needs, 
and create editable grocery lists based on the recipe's requirements.

## 2. What It Does

---

- The application's `Homepage` displays the names of all of the user's recipes and provides the following options:
  - `Full Recipe` option displays its respective recipe in its entirety
  - `Edit` option allows user to modify the recipe
  - `Delete` option removes the recipe from the application
  - `View Shopping List` option navigates to the user's personal shopping list
  - `Create Recipe` option allows user to create and customize their personal recipes
 
- The `Create Recipe` page provides a fillable form that allows user to create and customize their personal recipes
  - Input the `Recipe Name`, `Calories`, `Servings`, `Ingredients`, and `Recipe Steps`
  - Add ingredients to the table by entering the name, amount, and unit of measurement and clicking the `Add Ingredient` button
  - The recipe is saved when the user clicks the `Create Recipe` button
 
- The `Full Recipe` page, along with viewing the recipe, provides:
  - `View Shopping List` option
  - `Edit Recipe` option
  - `Add To Shopping List` option adds the recipe's ingredients to the user's shopping list
 
- The `Edit Recipe` page displays the recipe and allows user to modify its `Calories`, `Servings`, `Ingredients`, and `Recipe Steps`
  - Changes made to the recipe must be confirmed by clicking the `Save Changes` button
 
- The `Shopping List` page displays all items that have been added to it, along with their quantity and the options to:
  - Add or subtract from the item's quantity
  - Remove an item from the list
  - Clear the list entirely
  - Changes made to the list must be confirmed by clicking the `Save Changes` button  

## 3. How It Works

---

- Uses AWS Lambda to process requests from the webpage
- Java is used for backend design and function
- Javascript is used for frontend display and interaction
- DynamoDB houses the tables where the recipe and shopping list information is stored
- AWS CloudFormation/CloudFront

