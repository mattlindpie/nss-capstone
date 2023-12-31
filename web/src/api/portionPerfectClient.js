import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the nss.capstone.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class PortionPerfectClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createRecipe', 'getRecipe', 'getAllRecipes', 'deleteRecipe', 'addToShoppingList', 'getShoppingList', 'updateShoppingList'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Create a new recipe owned by the current user.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The recipe that has been created.
     */
    async createRecipe(recipeName, servings, recipeSteps, ingredients, calories, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create recipes.");
            const response = await this.axiosClient.post(`recipes`, {
                recipeName: recipeName,
                servings: servings,
                recipeSteps: recipeSteps,
                ingredients: ingredients,
                calories: calories
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.recipe;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

        async getRecipe(recipeName) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can view full recipe.");
                const response = await this.axiosClient.get(`recipes/${recipeName}`, {
                    headers: {
                       Authorization: `Bearer ${token}`
                    }}
                    );
                return response.data.recipe;
            } catch (error) {
                this.handleError(error)
            }
        }

    async getAllRecipes() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can get recipes.");
            const response = await this.axiosClient.get(`recipes`, {
                headers: {
                   Authorization: `Bearer ${token}`
                }}
                );
            return response.data.recipes;
        } catch (error) {
            this.handleError(error)
        }
    }

    async updateRecipe(recipeName, servings, recipeSteps, ingredients, calories, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create recipes.");
            const response = await this.axiosClient.put(`recipes/${recipeName}`, {
                recipeName: recipeName,
                servings: servings,
                recipeSteps: recipeSteps,
                ingredients: ingredients,
                calories: calories
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.recipe;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async deleteRecipe(recipeName) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete recipes.");
            const response = await this.axiosClient.delete(`recipes/${recipeName}`, {
                headers: {
                   Authorization: `Bearer ${token}`
                }}
                );
            return response.data.recipes;
        } catch (error) {
            this.handleError(error)
        }
    }

    async addToShoppingList(ingredientNames, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add to this shopping list.");
            const response = await this.axiosClient.post(`shoppingLists`, {
                ingredientNames: ingredientNames
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.shoppingList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getShoppingList() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve shopping list.");
            const response = await this.axiosClient.get(`shoppingLists`, {
                headers: {
                   Authorization: `Bearer ${token}`
                }}
                );
            return response.data.shoppingList;
        } catch (error) {
            this.handleError(error)
        }
    }

    async updateShoppingList(shoppingListItems) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add to this shopping list.");
            const response = await this.axiosClient.put(`shoppingLists`, {
                shoppingListItems: shoppingListItems
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.shoppingList;
        } catch (error) {
            this.handleError(error)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}