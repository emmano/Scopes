package me.emmano.scopes.app;

import me.emmano.scopesapi.Scope;
import me.emmano.scopes.app.services.GithubService;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Scope(baseActivityName = "BaseLoginFlowActivity", retrofitServices = GithubService.class, butterKnife = true)
public class LoginFlow {

}
