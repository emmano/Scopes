package me.emmano.scopes.app.modules;

import dagger.Module;
import dagger.Provides;
import me.emmano.scopes.app.models.AnotherDummyModel;
import me.emmano.scopes.app.signup.BaseSignUpFlowActivityModule;
import me.emmano.scopes.app.signup.SignupActivity;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@Module(injects = SignupActivity.class, addsTo = BaseSignUpFlowActivityModule.class)
public class SignupActivityModule {

    @Provides
    public AnotherDummyModel providesAnotherDummyModel(){
        return new AnotherDummyModel();
        
    }
    
}
