package nss.capstone.dependency;

import dagger.Component;
import nss.capstone.activity.*;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreateRecipeActivity
     */
    CreateRecipeActivity provideCreateRecipeActivity();

    /**
     * Provides the relevant activity.
     * @return GetAllRecipesActivity
     */
    GetAllRecipesActivity provideGetAllRecipesActivity();
 
    /**
     * Provides the relevant activity.
     * @return DeleteRecipeActivity
     */
    DeleteRecipeActivity provideDeleteRecipeActivity();
  
    /**
     * Provides the relevant activity.
     * @return GetRecipeActivity
     */
    GetRecipeActivity provideGetRecipeActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateRecipeActivity
     */
    UpdateRecipeActivity provideUpdateRecipeActivity();

    /**
     * Provides the relevant activity.
     * @return AddToShoppingListActivity
     */
    AddToShoppingListActivity provideAddToShoppingListActivity();

    /**
     * Provides the relevant activity.
     * @return GetShoppingListActivity
     */
    GetShoppingListActivity provideGetShoppingListActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateShoppingListActivity
     */
    UpdateShoppingListActivity provideUpdateShoppingListActivity();
}



