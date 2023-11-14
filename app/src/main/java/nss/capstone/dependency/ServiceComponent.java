package nss.capstone.dependency;

import dagger.Component;
import nss.capstone.activity.CreateRecipeActivity;
import nss.capstone.activity.DeleteRecipeActivity;
import nss.capstone.activity.GetAllRecipesActivity;
import nss.capstone.activity.GetRecipeActivity;

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
}
