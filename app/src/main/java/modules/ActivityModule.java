package modules;

import dagger.Module;
import me.emmano.scopes.app.MainActivity;
import scopes.BaseFlowActivityModule;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Module(injects = MainActivity.class, addsTo = BaseFlowActivityModule.class)
public class ActivityModule {
}
