package me.emmano.scopes.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import me.emmano.scopes.app.R;
import me.emmano.scopes.app.models.DummyModel;
import me.emmano.scopes.app.modules.ActivityModule;
import me.emmano.scopes.app.services.Repo;
import me.emmano.scopes.app.signup.SignupActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends me.emmano.scopes.app.login.BaseLoginFlowActivity {

    @InjectView(R.id.button)
    protected Button button;
    
    @Inject
    protected DummyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setText("Eureka!");
        Log.e(LoginActivity.class.getSimpleName(), model.toString());
        githubService.starGazers(new Callback<List<Repo>>() {
            @Override
            public void success(List<Repo> repos, Response response) {
                for (Repo repo : repos) {
                    Log.e(LoginActivity.class.getSimpleName(), repo.getLogIn());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    
    @OnClick(R.id.button)
    public void buttonClicked(){
        startActivity(new Intent(this, SignupActivity.class));
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
        return R.layout.login_activity;
    }
}
