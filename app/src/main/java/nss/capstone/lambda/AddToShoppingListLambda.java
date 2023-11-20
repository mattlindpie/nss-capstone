package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.AddToShoppingListRequest;
import nss.capstone.activity.results.AddToShoppingListResult;

public class AddToShoppingListLambda extends LambdaActivityRunner<AddToShoppingListRequest, AddToShoppingListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddToShoppingListRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddToShoppingListRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddToShoppingListRequest unauthenticatedRequest = input.fromBody(AddToShoppingListRequest.class);
                    return input.fromUserClaims(claims ->
                            AddToShoppingListRequest.builder()
                                    .withIngredientNames(unauthenticatedRequest.getIngredientNames())
                                    .withUserId(claims.get("email"))
                                    .build());

                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddToShoppingListActivity().handleRequest(request)
        );
    }


}
