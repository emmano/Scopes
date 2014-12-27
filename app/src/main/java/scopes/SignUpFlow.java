package scopes;

import me.emmano.scopesapi.Scope;
import modules.RestAdapterModule;
import services.GithubService;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@Scope(baseActivityName = "BaseSignUpFlowActivity", retrofitServices = GithubService.class,
        restAdapterModule = RestAdapterModule.class, butterKnife = false)
public class SignUpFlow {

}
