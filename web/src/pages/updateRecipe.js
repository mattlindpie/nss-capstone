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
        this.bindClassMethods(['mount', 'getRecipe', 'displayRecipe', 'getHTMLForSearchResults', 'buildIngredientsTable', 'displayRecipeSteps', 'addRow', 'toggleHide'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);

    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {
        // this.dataStore.addChangeListener(this.displayRecipe);

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

        const urlParams = new URLSearchParams(window.location.search);
        const recipeName = urlParams.get('recipeName');

        this.getRecipe(recipeName);

        const loadingNotification = document.getElementById('loading-notification');
        loadingNotification.innerHTML = 'Loading ' + recipeName + '...';
        this.toggleHide(loadingNotification);

        this.dataStore.addChangeListener(this.displayRecipe);
        this.toggleHide(loadingNotification);
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

    displayRecipe() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const recipeName = searchCriteria;

        let servings = searchResults.servings;
        let recipeSteps = searchResults.recipeSteps;
        let ingredientList = searchResults.ingredients;
        let calories = searchResults.calories;

        const recipeNameDisplay = document.getElementById('recipe-name-display');
        const searchResultsDisplay = document.getElementById('search-results-container');
        const ingredientsDisplay = document.getElementById('ingredients-display');
        const recipeStepsDisplay = document.getElementById('steps-display');

        if (searchCriteria === '') {

            recipeNameDisplay.innerHTML = '';

        } else {
            recipeNameDisplay.innerHTML = `${searchCriteria}`;

            this.getHTMLForSearchResults(calories, servings); 

            recipeStepsDisplay.innerHTML = '';
            this.displayRecipeSteps(recipeSteps, recipeStepsDisplay);

            ingredientsDisplay.innerHTML = '';
            this.buildIngredientsTable(ingredientList, ingredientsDisplay);

            const updateNotification = document.getElementById('update-notification');
            updateNotification.innerHTML = "Updating " + recipeName;

            const updateButton = document.getElementById('submit-button');
            updateButton.addEventListener('click', async (evt) => {
                evt.preventDefault();

                calories = document.getElementById('calories').value;

                servings = document.getElementById('servings').value;

                recipeSteps = document.getElementById('recipe-steps').value;
                recipeSteps = recipeSteps.split(/\s*,\s*/);
                
                const updateNotification = document.getElementById('update-notification');
                updateNotification.innerHTML = "Updating " + recipeName + "...";
                this.toggleHide(updateNotification);
                
                await this.client.updateRecipe(recipeName, servings, recipeSteps, ingredientList, calories, (error) => {
                    errorMessageDisplay.innerText = `Error: ${error.message}`;
                    errorMessageDisplay.classList.remove('hidden');
                });
                updateNotification.innerHTML = "Recipe for " + recipeName + " updated successfully";
                setTimeout(() => {
                    this.toggleHide(updateNotification)
                }, 5000);
            })
        }
    }

    getHTMLForSearchResults(calories, servings) {
        const servingsElement = document.getElementById('servings');
        servingsElement.value = servings;
        const caloriesElement = document.getElementById('calories');
        caloriesElement.value = calories;
    }

    buildIngredientsTable(ingredientList, ingredientsDisplay) {

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
        addButton.addEventListener('click', (evt) => {
            evt.preventDefault();

            const ingredientName = document.getElementById('ingredient-name').value;
            const amount = document.getElementById('amount').value;
            const unit = document.getElementById('unit').value;

            const ingredient = {ingredientName: ingredientName, amount: parseFloat(amount), unitOfMeasurement: unit};

            ingredientList.push(ingredient);
            this.addRow(tableBody, ingredient, ingredientList);
            
            document.getElementById('ingredient-name').value = '';
            document.getElementById('amount').value = '';
            
        })

        ingredientsDisplay.appendChild(table);

        return ingredientsDisplay.outerHTML;
    }

    displayRecipeSteps(recipeSteps, recipeStepsDisplay) {
        console.log(recipeSteps);


        let stepsInput = document.getElementById('recipe-steps');
        stepsInput.value = recipeSteps;

        if(recipeStepsDisplay.innerHTML === '') {
            const listTitle = document.createElement('h4');
            listTitle.textContent = 'Recipe Steps:';
            recipeStepsDisplay.appendChild(listTitle);

            const stepList = document.createElement('ol');
    
            recipeSteps.forEach((step) => {
                console.log(step);
                const listItem = document.createElement('li');
                listItem.textContent = step;
                stepList.appendChild(listItem);
            });

            const saveStepsButton = document.getElementById('save-steps-button');
            saveStepsButton.addEventListener('click', (evt) => {
                evt.preventDefault();

                let steps = stepsInput.value;
                recipeSteps = steps.split(/\s*,\s*/);

                stepList.innerHTML = '';

                recipeSteps.forEach((step) => {
                    const listItem = document.createElement('li');
                    listItem.textContent = step;
                    stepList.appendChild(listItem);
                });
                recipeStepsDisplay.appendChild(stepList);
            })
            
            recipeStepsDisplay.appendChild(stepList);
        }
        return recipeSteps;
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
            amount = amount + .25;
            amountCell.textContent = amount;
            ingredient.amount = amount; 
        });

        const subtractButton = document.createElement('button');
        subtractButton.textContent = "-";
        subtractButton.addEventListener('click', async () => {
            if(amount - .25 > 0) {
                amount = amount - .25;
                amountCell.textContent = amount;
                ingredient.amount = amount; 
            }
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

    toggleHide(HTMLNotification) {
        if (HTMLNotification.style.display === "block") {
            HTMLNotification.style.display = "none";
        } else {
            HTMLNotification.style.display = "block";
        }
    }
}

const main = async () => {
    const updateRecipe = new UpdateRecipe();
    updateRecipe.mount();
};

window.addEventListener('DOMContentLoaded', main);