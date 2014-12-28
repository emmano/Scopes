package me.emmano.scopes.app.modules;

import dagger.Module;
import dagger.Provides;
import me.emmano.scopes.app.login.BaseLoginFlowActivityModule;
import me.emmano.scopes.app.login.LoginActivity;
import me.emmano.scopes.app.models.DummyModel;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Module(injects = LoginActivity.class, addsTo = BaseLoginFlowActivityModule.class)
public class ActivityModule {
    
    @Provides
    public DummyModel providesDummyModel(){
        return new DummyModel();
    }

}
