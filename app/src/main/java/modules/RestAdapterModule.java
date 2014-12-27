package modules;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Module(library = true)
public class RestAdapterModule {

    @Provides
    public RestAdapter providesRestAdapter() {
        return new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.github.com/repos/emmano").build();
    }

}
