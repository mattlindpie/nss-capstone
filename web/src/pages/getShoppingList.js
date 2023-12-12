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
        this.bindClassMethods(['mount', 'loadShoppingList', 'displayShoppingList', 'buildTable', 'toggleHide'], this);
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
        console.log(searchResults);

        const shoppingListTableHTML = document.getElementById('shopping-list-table');

        const ingredients = searchResults.shoppingListItems;
        const ingredientsMap = new Map(Object.entries(ingredients));
        
        if (ingredientsMap.size < 1) {
            shoppingListTableHTML.innerHTML = 'Shopping list is empty';
        } else {
            this.buildTable(ingredientsMap, shoppingListTableHTML);
        }
    }

    buildTable(ingredientsMap, shoppingListTable) {
        
        const table = document.createElement('table');

        const tableHeader = table.createTHead();
        const headerRow = tableHeader.insertRow();

        const nameHeader = document.createElement('th');
        nameHeader.textContent = 'Ingredient';
        headerRow.appendChild(nameHeader);

        const subtractHeader = document.createElement('th');
        subtractHeader.textContent = '';
        headerRow.appendChild(subtractHeader);

        const quantityHeader = document.createElement('th');
        quantityHeader.textContent = 'Qty';
        headerRow.appendChild(quantityHeader);

        const addHeader = document.createElement('th');
        addHeader.textContent = '';
        headerRow.appendChild(addHeader);

        const tableBody = table.createTBody();

        for (let [key, value] of ingredientsMap) {
            const row = tableBody.insertRow();

            const ingredientNameCell = row.insertCell(0);
            const subtractOneFromQuantityCell = row.insertCell(1);
            const quantityCell = row.insertCell(2);
            const addOneToQuantityCell = row.insertCell(3);
            const removeCell = row.insertCell(4);

            ingredientNameCell.textContent = key;
            quantityCell.textContent = value;

            const addButton = document.createElement('button');
            addButton.textContent = "+";
            addButton.addEventListener('click', async () => {
                value = value + 1;
                quantityCell.textContent = value;
                ingredientsMap.set(key, value);
            });

            const subtractButton = document.createElement('button');
            subtractButton.textContent = "-";
            subtractButton.addEventListener('click', async () => {
                value = value - 1;
                quantityCell.textContent = value;
                ingredientsMap.set(key, value);
            });

            const removeButton = document.createElement('button');
            removeButton.textContent = "Remove";
            removeButton.addEventListener('click', async () => {
                ingredientsMap.delete(key);
                row.style.display = "none";                
            });

            subtractOneFromQuantityCell.appendChild(subtractButton);
            addOneToQuantityCell.appendChild(addButton);
            removeCell.appendChild(removeButton);
        }

        const submitButton = document.getElementById('submit-button');

        submitButton.addEventListener('click', async(evt) => {
            evt.preventDefault();
            const mapObject = Object.fromEntries(ingredientsMap);

            const updateNotification = document.getElementById('update-notification');
            updateNotification.innerHTML = "Updating shopping list...";
            this.toggleHide();
            await this.client.updateShoppingList(mapObject);
            
            updateNotification.innerHTML = "Shopping list updated successfully";
            setTimeout(this.toggleHide, 5000);

        })

        const clearButton = document.getElementById('clear-button');
        clearButton.addEventListener('click', async(evt) => {
            evt.preventDefault();
            let clearYN = confirm("Clear full shopping list?");
            if (clearYN === true) {
                ingredientsMap.clear();
                const mapObject = Object.fromEntries(ingredientsMap);
                tableBody.style.display = "none";
                await this.client.updateShoppingList(mapObject);
            }

        });

        shoppingListTable.appendChild(table);

    }

    toggleHide() {
        const form = document.getElementById("submit-button");
        if (form.style.display === "block") {
            form.style.display = "none";
        } else {
            form.style.display = "block";
        }
    }

}

const main = async () => {
    const getShoppingList = new GetShoppingList();
    getShoppingList.mount();
};

window.addEventListener('DOMContentLoaded', main);