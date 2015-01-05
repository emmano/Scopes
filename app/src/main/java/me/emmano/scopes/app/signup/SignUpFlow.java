package me.emmano.scopes.app.signup;

import me.emmano.scopes.app.modules.RestAdapterModule;
import me.emmano.scopes.app.services.GithubService;
import me.emmano.scopesapi.DaggerScope;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@DaggerScope(baseActivityName = "BaseSignUpFlowActivity", retrofitServices = GithubService.class,
        restAdapterModule = RestAdapterModule.class, butterKnife = false)
public class SignUpFlow {

}
