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
        this.bindClassMethods(['mount', 'submit'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {
        document.getElementById('createRecipe').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();
    }

     /**
     * Method to run when the create recipe submit button is pressed. Call the nss.capstone to create the
     * recipe.
     */
    async submit(evt) {
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


        const recipe = await this.client.createRecipe(recipeName, servings, ingredients, recipeSteps, calories, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('recipe', recipe);
    }
}

const main = async () => {
    const createRecipe = new CreateRecipe();
    createRecipe.mount();
};

window.addEventListener('DOMContentLoaded', main);