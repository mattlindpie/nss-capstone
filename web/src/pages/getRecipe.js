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
class GetRecipe extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'getRecipe', 'displayRecipe', 'getHTMLForSearchResults', 'getIngredientsList', 'displayRecipeSteps', 'toggleHide'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        // this.dataStore.addChangeListener(this.displayRecipe);

    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {

        document.getElementById('add-to-list-button').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

        const urlParams = new URLSearchParams(window.location.search);
        const recipeName = urlParams.get('recipeName');
        
        this.getRecipe(recipeName);

        this.dataStore.addChangeListener(this.displayRecipe);

        const editRecipeButton = document.getElementById('edit-recipe-button');
        editRecipeButton.href = `updateRecipe.html?recipeName=${recipeName}`;

    }

    async getRecipe(recipeName) {
                if (recipeName) {

                    const loadingNotification = document.getElementById('loading-notification');
                    loadingNotification.innerHTML = 'Loading ' + recipeName + '...';
                    this.toggleHide(loadingNotification);

                    const recipe = await this.client.getRecipe(recipeName);

                    this.dataStore.setState({
                        [SEARCH_CRITERIA_KEY]: recipeName,
                        [SEARCH_RESULTS_KEY]: recipe,
                    });

                    this.toggleHide(loadingNotification);

                } else {
                    this.dataStore.setState(EMPTY_DATASTORE_STATE);
                }
    }
    
    async submit(evt) {
        evt.preventDefault();
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const ingredientList = searchResults.ingredients;
        const ingredientNames = [];
        ingredientList.forEach((ingredient) => {

            const ingredientName = ingredient.ingredientName;
            ingredientNames.push(ingredientName);

        });

        const addNotification = document.getElementById('add-notification');
        addNotification.innerHTML = "Adding ingredients...";
        this.toggleHide(addNotification);
        await this.client.addToShoppingList(ingredientNames, (error) => {
            createButton.innerText = origButtonText;
        });

        addNotification.innerHTML = "Ingredients added to list";
        setTimeout(() => {
            this.toggleHide(addNotification);
        }, 5000);
    }

    displayRecipe() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const recipeNameDisplay = document.getElementById('recipe-name-display');
        const searchResultsDisplay = document.getElementById('search-results-container');
        const ingredientsDisplay = document.getElementById('ingredients-display');
        const recipeStepsDisplay = document.getElementById('steps-display');

        if (searchCriteria === '') {

            recipeNameDisplay.innerHTML = '';

        } else {
            recipeNameDisplay.innerHTML = `${searchCriteria}`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults); 
            ingredientsDisplay.innerHTML = this.getIngredientsList(searchResults);
            recipeStepsDisplay.innerHTML = this.displayRecipeSteps(searchResults);
        }

    }

    getHTMLForSearchResults(searchResults) {
        const calories = searchResults.calories;
        const servings = searchResults.servings;

        const container = document.getElementById('search-results-container');

        const servingsElement = document.createElement('p');
        servingsElement.textContent = `Servings: ${servings}`;

        const caloriesElement = document.createElement('p');
        caloriesElement.textContent = `Calories: ${calories} | ` + servingsElement.textContent;
        container.appendChild(caloriesElement);       

        return container.outerHTML;
    }

    getIngredientsList(searchResults) {

        const ingredientList = searchResults.ingredients;

        const container = document.getElementById('ingredients-display');

        const tableTitle = document.createElement('h4');
        tableTitle.textContent = 'Ingredients:';
        container.appendChild(tableTitle);

        const table = document.createElement('table');

        const tableHeader = table.createTHead();
        const headerRow = tableHeader.insertRow();

        const nameHeader = document.createElement('th');
        nameHeader.textContent = 'Ingredient';
        headerRow.appendChild(nameHeader);

        const amountHeader = document.createElement('th');
        amountHeader.textContent = 'Amount';
        headerRow.appendChild(amountHeader);

        const unitHeader = document.createElement('th');
        unitHeader.textContent = 'Unit';
        headerRow.appendChild(unitHeader);

        const tableBody = table.createTBody();

        ingredientList.forEach((ingredient) => {
            const row = tableBody.insertRow();

            const nameCell = row.insertCell(0);
            const amountCell = row.insertCell(1);
            const unitCell = row.insertCell(2);

            nameCell.textContent = ingredient.ingredientName;
            amountCell.textContent = ingredient.amount;
            unitCell.textContent = ingredient.unitOfMeasurement;

        });

        container.appendChild(table);

        return container.outerHTML;
    }

    displayRecipeSteps(searchResults) {
        const recipeSteps = searchResults.recipeSteps;

        const container = document.getElementById('steps-display');

        const listTitle = document.createElement('h4');
        listTitle.textContent = 'Recipe Steps:';
        container.appendChild(listTitle);

        const stepList = document.createElement('ol');

        recipeSteps.forEach((step) => {
            const listItem = document.createElement('li');
            listItem.textContent = step;
            stepList.appendChild(listItem);
        });

        container.appendChild(stepList);

        return container.outerHTML;
    }

    toggleHide(HTMLNotification) {
        if (HTMLNotification.style.display === "block") {
            HTMLNotification.style.display = "none";
        } else {
            HTMLNotification.style.display = "block";
        }
    }
}

const main = async () => {
    const getRecipe = new GetRecipe();
    getRecipe.mount();
};

window.addEventListener('DOMContentLoaded', main);