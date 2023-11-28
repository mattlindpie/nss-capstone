package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.UpdateShoppingListRequest;
import nss.capstone.activity.results.UpdateShoppingListResult;

public class UpdateShoppingListLambda extends LambdaActivityRunner<UpdateShoppingListRequest, UpdateShoppingListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateShoppingListRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateShoppingListRequest> input, Context context) {
        return super.runActivity(
                () -> {UpdateShoppingListRequest unauthenticatedRequest = input.fromBody(UpdateShoppingListRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateShoppingListRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withShoppingListItems(unauthenticatedRequest.getShoppingListItems())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateShoppingListActivity().handleRequest(request));
    }

    
}
