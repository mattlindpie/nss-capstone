package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.UpdateRecipeRequest;
import nss.capstone.activity.results.UpdateRecipeResult;

public class UpdateRecipeLambda extends LambdaActivityRunner<UpdateRecipeRequest, UpdateRecipeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateRecipeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateRecipeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateRecipeRequest unauthenticatedRequest = input.fromBody(UpdateRecipeRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateRecipeRequest.builder()
                                    .withRecipeName(unauthenticatedRequest.getRecipeName())
                                    .withServings(unauthenticatedRequest.getServings())
                                    .withIngredients(unauthenticatedRequest.getIngredients())
                                    .withRecipeSteps(unauthenticatedRequest.getRecipeSteps())
                                    .withCalories(unauthenticatedRequest.getCalories())
                                    .withUserId(claims.get("email"))
                                    .build());

                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateRecipeActivity().handleRequest(request)
        );
    }
}
