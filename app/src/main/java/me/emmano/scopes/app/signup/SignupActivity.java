package me.emmano.scopes.app.signup;

import android.os.Bundle;

import javax.inject.Inject;

import me.emmano.scopes.app.R;
import me.emmano.scopes.app.models.AnotherDummyModel;
import me.emmano.scopes.app.modules.SignupActivityModule;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
public class SignupActivity extends me.emmano.scopes.app.signup.BaseSignUpFlowActivity {

    @Inject
    protected AnotherDummyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new SignupActivityModule()};
    }
}
