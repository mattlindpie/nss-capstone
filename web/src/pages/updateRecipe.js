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
class UpdateRecipe extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'getRecipe', 'displayRecipe', 'getHTMLForSearchResults', 'getIngredientsList', 'displayRecipeSteps', 'addRow'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);

    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {
        this.dataStore.addChangeListener(this.displayRecipe);



        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

        const urlParams = new URLSearchParams(window.location.search);
        const recipeName = urlParams.get('recipeName');

        this.getRecipe(recipeName);
    }

    async getRecipe(recipeName) {
                if (recipeName) {
                    const recipe = await this.client.getRecipe(recipeName);

                    this.dataStore.setState({
                        [SEARCH_CRITERIA_KEY]: recipeName,
                        [SEARCH_RESULTS_KEY]: recipe,
                    });
                } else {
                    this.dataStore.setState(EMPTY_DATASTORE_STATE);
                }
    }
    
    async submit(recipeName, servings, recipeStepsText, ingredients, calories) {

        let recipeSteps;
        if (recipeStepsText.length < 1) {
            recipeSteps = null;
        } else {
            recipeSteps = recipeStepsText.split(/\s*,\s*/);
        }

        const recipe = await this.client.updateRecipe(recipeName, servings, recipeSteps, ingredients, calories,(error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('recipe', recipe);
        // this.getRecipe();
    }

    displayRecipe() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        let ingredientList = searchResults.ingredients;
        let servings = 4;
        let recipeSteps = searchResults.recipeSteps;

        const recipeNameDisplay = document.getElementById('recipe-name-display');
        const searchResultsDisplay = document.getElementById('search-results-container');
        const ingredientsDisplay = document.getElementById('ingredients-display');
        const recipeStepsDisplay = document.getElementById('steps-display');

        if (searchCriteria === '') {

            recipeNameDisplay.innerHTML = '';

        } else {
            recipeNameDisplay.innerHTML = `${searchCriteria}`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults); 
            ingredientsDisplay.innerHTML = '';
            this.getIngredientsList(ingredientList, ingredientsDisplay);
            recipeStepsDisplay.innerHTML = this.displayRecipeSteps(searchResults);

            const submitButton = document.getElementById('submit-button');
            submitButton.addEventListener('click', this.submit(SEARCH_CRITERIA_KEY, servings, recipeSteps, ingredientList, calories));
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

    getIngredientsList(ingredientList, ingredientsDisplay) {

        // const ingredientList = searchResults.ingredients;

        const tableTitle = document.createElement('h4');
        tableTitle.textContent = 'Ingredients:';
        ingredientsDisplay.appendChild(tableTitle);

        const table = document.createElement('table');

        const tableHeader = table.createTHead();
        const headerRow = tableHeader.insertRow();

        const nameHeader = document.createElement('th');
        nameHeader.textContent = 'Ingredient';
        headerRow.appendChild(nameHeader);

        const subtractHeader = document.createElement('th');
        subtractHeader.textContent = '';
        headerRow.appendChild(subtractHeader);

        const amountHeader = document.createElement('th');
        amountHeader.textContent = 'Amount';
        headerRow.appendChild(amountHeader);

        const addHeader = document.createElement('th');
        addHeader.textContent = '';
        headerRow.appendChild(addHeader);

        const unitHeader = document.createElement('th');
        unitHeader.textContent = 'Unit';
        headerRow.appendChild(unitHeader);

        const tableBody = table.createTBody();

        for (let ingredient of ingredientList) {
            this.addRow(tableBody, ingredient, ingredientList);

        }

        const addButton = document.getElementById('add-button');
        addButton.addEventListener('click', async (evt) => {
            evt.preventDefault();

            const ingredientName = document.getElementById('ingredient-name').value;
            const amount = document.getElementById('amount').value;
            const unit = document.getElementById('unit').value;

            const ingredient = {ingredientName: ingredientName, amount: parseFloat(amount), unitOfMeasurement: unit};

            ingredientList.push(ingredient);
            console.log(ingredientList);
            this.addRow(tableBody, ingredient, ingredientList);
            
        })

        ingredientsDisplay.appendChild(table);

        return ingredientsDisplay.outerHTML;
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

    addRow(tableBody, ingredient, ingredientList) {
        const ingredientName = ingredient.ingredientName;
        let amount = ingredient.amount;
        const unit = ingredient.unitOfMeasurement;

        const row = tableBody.insertRow();

        const nameCell = row.insertCell(0);
        const subtractOneFromAmountCell = row.insertCell(1);
        const amountCell = row.insertCell(2);
        const addOneToAmountCell = row.insertCell(3);
        const unitCell = row.insertCell(4);
        const removeCell = row.insertCell(5);

        nameCell.textContent = ingredientName;
        amountCell.textContent = amount;
        unitCell.textContent = unit;

        const addOneButton = document.createElement('button');
        addOneButton.textContent = "+";
        addOneButton.addEventListener('click', async () => {
            amount++;
            amountCell.textContent = amount;
            ingredient.amount = amount; 
            console.log(amount)
            console.log(ingredient);
        });

        const subtractButton = document.createElement('button');
        subtractButton.textContent = "-";
        subtractButton.addEventListener('click', async () => {
            amount--;
            amountCell.textContent = amount;
            ingredient.amount = amount; 
            console.log(amount)
            console.log(ingredient);
        });

        const removeButton = document.createElement('button');
        removeButton.textContent = "Remove";
        removeButton.addEventListener('click', async () => {
            ingredientList.pop(ingredient);
            row.style.display = "none";                
        });

        removeCell.appendChild(removeButton);
        subtractOneFromAmountCell.appendChild(subtractButton);
        addOneToAmountCell.appendChild(addOneButton);


    }


}

const main = async () => {
    const updateRecipe = new UpdateRecipe();
    updateRecipe.mount();
};

window.addEventListener('DOMContentLoaded', main);