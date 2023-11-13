package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.GetAllRecipesRequest;
import nss.capstone.activity.results.GetAllRecipesResult;

public class GetAllRecipesLambda extends LambdaActivityRunner<GetAllRecipesRequest, GetAllRecipesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllRecipesRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllRecipesRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllRecipesRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllRecipesActivity().handleRequest(request)
        );
    }

}
