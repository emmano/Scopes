package me.emmano.scopes.app;

import android.app.Application;

import dagger.ObjectGraph;
import me.emmano.scopesapi.ApplicationGraph;
import modules.ApplicationModule;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
public class ScopedApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new ApplicationModule());
    }

    @ApplicationGraph(applicationModule = ApplicationModule.class)
    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
