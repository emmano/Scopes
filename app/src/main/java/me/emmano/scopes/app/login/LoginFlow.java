package me.emmano.scopes.app.login;

import me.emmano.scopes.app.services.GithubService;
import me.emmano.scopesapi.DaggerScope;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@DaggerScope(baseActivityName = "BaseLoginFlowActivity", retrofitServices = GithubService.class, butterKnife = true)
public class LoginFlow {

}
