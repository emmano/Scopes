package scopes;

import me.emmano.scopesapi.Scope;
import services.GithubService;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
@Scope(baseActivityName = "BaseLoginFlowActivity", retrofitServices = GithubService.class, butterKnife = true)
public class LoginFlow {

}
