package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.GetRecipesByCaloriesRequest;
import nss.capstone.activity.results.GetRecipesByCaloriesResult;

public class GetRecipesByCaloriesLambda
        extends LambdaActivityRunner<GetRecipesByCaloriesRequest, GetRecipesByCaloriesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetRecipesByCaloriesRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetRecipesByCaloriesRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetRecipesByCaloriesRequest unauthenticatedRequest = input.fromBody(GetRecipesByCaloriesRequest.class);
                    return input.fromUserClaims(claims ->
                            GetRecipesByCaloriesRequest.builder()
                                    .withMinCalories(unauthenticatedRequest.getMinCalories())
                                    .withMaxCalories(unauthenticatedRequest.getMaxCalories())
                                    .withUserId(claims.get("email"))
                                    .build());

                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetRecipesByCaloriesActivity().handleRequest(request)
        );
    }


}
