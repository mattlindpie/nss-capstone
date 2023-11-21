package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.GetShoppingListRequest;
import nss.capstone.activity.results.GetShoppingListResult;

public class GetShoppingListLambda
        extends LambdaActivityRunner<GetShoppingListRequest, GetShoppingListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetShoppingListRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetShoppingListRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetShoppingListRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetShoppingListActivity().handleRequest(request)
        );
    }
}
