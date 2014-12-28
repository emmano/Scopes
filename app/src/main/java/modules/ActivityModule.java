package modules;

import dagger.Module;
import me.emmano.scopes.app.MainActivity;
import scopes.BaseLoginFlowActivityModule;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Module(injects = MainActivity.class, addsTo = BaseLoginFlowActivityModule.class)
public class ActivityModule {}
