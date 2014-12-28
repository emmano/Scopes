package me.emmano.scopes.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import me.emmano.scopes.app.models.DummyModel;
import me.emmano.scopes.app.modules.ActivityModule;
import me.emmano.scopes.app.services.Repo;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends me.emmano.scopes.app.BaseLoginFlowActivity {

    @InjectView(R.id.text)
    protected TextView textView;
    
    @Inject
    protected DummyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText("Eureka!");
        Log.e(MainActivity.class.getSimpleName(), model.toString());
        githubService.starGazers(new Callback<List<Repo>>() {
            @Override
            public void success(List<Repo> repos, Response response) {
                for (Repo repo : repos) {
                    Log.e(MainActivity.class.getSimpleName(), repo.getLogIn());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new ActivityModule()};
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
