package scopes;

import me.emmano.scopesapi.Scope;
import services.GithubService;

/**
 * Created by emmanuelortiguela on 12/27/14.
 */
@Scope(baseActivityName = "BaseSignUpFlowActivity", retrofitServices = GithubService.class,
        restAdapterModule = "modules.RestAdapterModule.class", butterKnife = false)
public class SignUpFlow {

}
