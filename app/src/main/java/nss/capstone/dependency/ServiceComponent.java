package nss.capstone.dependency;

import dagger.Component;
import nss.capstone.activity.CreateRecipeActivity;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreateRecipeActivity
     */
    CreateRecipeActivity provideCreateRecipeActivity();
}
