package modules;

import dagger.Module;
import me.emmano.scopes.app.ScopedApplication;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@Module(injects = ScopedApplication.class, includes = RestAdapterModule.class, library = true)
public class ApplicationModule {}
