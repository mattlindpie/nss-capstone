import PortionPerfectClient from '../api/portionPerfectClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

/**
 * Logic needed for the create recipe page of the website.
 */
class GetAllRecipes extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadRecipes', 'displayRecipes', 'buildTable', 'submit'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.displayRecipes = this.displayRecipes.bind(this);
    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {
        document.getElementById('createRecipe').addEventListener('click', this.submit);

        this.dataStore.addChangeListener(this.displayRecipes);

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

        this.loadRecipes();
    }

    async loadRecipes() {
        console.log("IN LOAD RECIPES");
        const recipes = await this.client.getAllRecipes();
        this.dataStore.setState({
            [SEARCH_CRITERIA_KEY]: "recipes",
            [SEARCH_RESULTS_KEY]: recipes
        });
    }

    displayRecipes() {
        console.log("IN DISPLAY RECIPES");

        const recipeList = this.dataStore.get(SEARCH_RESULTS_KEY);

        const recipesTableHTML = document.getElementById('recipes-table');
        recipesTableHTML.innerHTML = '';
        this.buildTable(recipeList, recipesTableHTML);
    }
    

    buildTable(recipeList, recipesTable) {
        console.log("IN BUILD TABLE");

        for (const recipe of recipeList) {
            const row = document.createElement('tr');

            const recipeNameCell = document.createElement('td');
            const recipeName = `${recipe.recipeName}`;
            recipeNameCell.textContent = recipeName;

            row.appendChild(recipeNameCell);

            const fullRecipeButtonCell = document.createElement('td');
            const fullRecipeButton = document.createElement('button');

            fullRecipeButton.textContent = 'Full Recipe';
            fullRecipeButton.className = 'button';
            fullRecipeButton.addEventListener('click', () => {
                window.location.href = '/fullRecipe.html?recipeName=' + recipeName;
            });
            fullRecipeButtonCell.appendChild(fullRecipeButton);
            row.appendChild(fullRecipeButtonCell);

            const updateButtonCell = document.createElement('td');
            const updateButton = document.createElement('button');

            updateButton.textContent = 'Edit';
            updateButton.className = 'button';
            updateButton.addEventListener('click', () => {
                window.location.href = '/updateRecipe.html?recipeName=' + recipeName;
            });
            updateButtonCell.appendChild(updateButton);
            row.appendChild(updateButtonCell);

            const deleteButtonCell = document.createElement('td');
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';

            deleteButton.className = 'button';
            deleteButton.addEventListener('click', async () => {
                 let deleteYN = confirm("Are you sure you want to delete this recipe?");
                 if (deleteYN === true) {
                    await this.client.deleteRecipe(recipeName);
                      this.loadRecipes();
                 }
            });
            deleteButtonCell.appendChild(deleteButton);
            row.appendChild(deleteButtonCell);

            recipesTable.appendChild(row);
        }
    }

     /**
     * Method to run when the create recipe submit button is pressed. Call the nss.capstone to create the
     * recipe.
     */
     async submit(evt) {
        console.log("SUBMIT METHOD");
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('createRecipe');
        const origButtonText = createButton.innerText;

        const recipeName = document.getElementById('recipeName').value;
        const ingredients = document.getElementById('ingredients').value;
        const recipeSteps = document.getElementById('recipeSteps').value;
        const servings = document.getElementById('servings').value;
        const calories = document.getElementById('calories').value;


        const recipe = await this.client.createRecipe(recipeName, servings, recipeSteps, ingredients, calories, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('recipe', recipe);
    }
}

const main = async () => {
    console.log("IN MAIN");
    const getAllRecipes = new GetAllRecipes();
    getAllRecipes.mount();
};

window.addEventListener('DOMContentLoaded', main);