package me.emmano.scopes.app.signup;

import me.emmano.scopesapi.Scope;
import me.emmano.scopes.app.modules.RestAdapterModule;
import me.emmano.scopes.app.services.GithubService;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@Scope(baseActivityName = "BaseSignUpFlowActivity", retrofitServices = GithubService.class,
        restAdapterModule = RestAdapterModule.class, butterKnife = false)
public class SignUpFlow {

}
