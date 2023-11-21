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
class GetShoppingList extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'loadShoppingList', 'displayShoppingList', 'buildTable'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        
        this.header = new Header(this.dataStore);

    }
    
    /**
     * Add the header to the page and load the PortionPerfectClient.
     */
    mount() {

        this.dataStore.addChangeListener(this.displayShoppingList);

        this.header.addHeaderToPage();

        this.client = new PortionPerfectClient();

        this.loadShoppingList();

    }

    async loadShoppingList() {
        const shoppingList = await this.client.getShoppingList();
            this.dataStore.setState({
                [SEARCH_CRITERIA_KEY]: "shoppingList",
                [SEARCH_RESULTS_KEY]: shoppingList,
            });
    }

    displayShoppingList() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const shoppingListTableHTML = document.getElementById('shopping-list-table');
        shoppingListTableHTML.innerHTML = '';
        this.buildTable(searchResults, shoppingListTableHTML)
    }

    buildTable(searchResults, shoppingListTable) {
        const ingredients = searchResults.shoppingListItems;
        const ingredientsMap = new Map(Object.entries(ingredients));
        
        const table = document.createElement('table');

        const tableHeader = table.createTHead();
        const headerRow = tableHeader.insertRow();

        const nameHeader = document.createElement('th');
        nameHeader.textContent = 'Ingredient';
        headerRow.appendChild(nameHeader);

        const quantityHeader = document.createElement('th');
        quantityHeader.textContent = 'Quantity';
        headerRow.appendChild(quantityHeader);

        const tableBody = table.createTBody();

        for (const [key, value] of ingredientsMap) {
            const row = tableBody.insertRow();

            const ingredientNameCell = row.insertCell(0);
            const quantityCell = row.insertCell(1);

            ingredientNameCell.textContent = key;
            quantityCell.textContent = value;
        }

        shoppingListTable.appendChild(table);

    }

}

const main = async () => {
    const getShoppingList = new GetShoppingList();
    getShoppingList.mount();
};

window.addEventListener('DOMContentLoaded', main);