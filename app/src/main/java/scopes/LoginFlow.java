package scopes;

import me.emmano.scopesapi.Scope;
import modules.RestAdapterModule;
import services.GithubService;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Scope(baseActivityName = "BaseLoginFlowActivity", retrofitServices = GithubService.class,
        restAdapterModule = RestAdapterModule.class, butterKnife = true)
public class LoginFlow {

}
