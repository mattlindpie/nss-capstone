import PortionPerfectClient from '../api/portionPerfectClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create recipe page of the website.
 */
class CreateRecipe extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'createRecipe','addRow'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {

        this.createRecipe();

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

    }

        createRecipe() {

            const ingredientInputTable = document.getElementById('ingredients-table');
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
    
            table.appendChild(headerRow);
    
            const tableBody = table.createTBody();
        
            ingredientInputTable.appendChild(table);

            let ingredientArray = [];

            const addButton = document.getElementById('add-button');
            addButton.addEventListener('click', async (evt) => {
                evt.preventDefault();

                const ingredientName = document.getElementById('ingredient-name').value;
                const amount = document.getElementById('amount').value;
                const unit = document.getElementById('unit').value;
    
                const ingredient = {ingredientName: ingredientName, amount: amount, unitOfMeasurement: unit};
    
                ingredientArray.push(ingredient);
                console.log(ingredientArray);

                this.addRow(tableBody, ingredient);
                
            })

            const submitButton = document.getElementById('submit-button');
            submitButton.addEventListener('click', async (evt) => {
                evt.preventDefault();
    
                const errorMessageDisplay = document.getElementById('error-message');
                errorMessageDisplay.innerText = ``;
                errorMessageDisplay.classList.add('hidden');
        
                const recipeName = document.getElementById('recipeName').value;
                const ingredients = ingredientArray;
                const recipeStepsText = document.getElementById('recipeSteps').value;
                const servings = document.getElementById('servings').value;
                const calories = document.getElementById('calories').value;
        
                let recipeSteps;
                if (recipeStepsText.length < 1) {
                    recipeSteps = null;
                } else {
                    recipeSteps = recipeStepsText.split(/\s*,\s*/);
                }
        
                const recipe = await this.client.createRecipe(recipeName, servings, recipeSteps, ingredients, calories, (error) => {
                    errorMessageDisplay.innerText = `Error: ${error.message}`;
                    errorMessageDisplay.classList.remove('hidden');
                });
                this.dataStore.set('recipe', recipe);
            })
        }
    
    addRow(tableBody, ingredient) {
        console.log(ingredient);
        const row = tableBody.insertRow();

        const nameCell = row.insertCell(0);
        const amountCell = row.insertCell(1);
        const unitCell = row.insertCell(2);

        nameCell.textContent = ingredient.ingredientName;
        amountCell.textContent = ingredient.amount;
        unitCell.textContent = ingredient.unitOfMeasurement;

    }

}

const main = async () => {
    const createRecipe = new CreateRecipe();
    createRecipe.mount();
};

window.addEventListener('DOMContentLoaded', main);