package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.CreateRecipeRequest;
import nss.capstone.activity.results.CreateRecipeResult;

public class CreateRecipeLambda extends LambdaActivityRunner<CreateRecipeRequest, CreateRecipeResult>
                                implements RequestHandler<AuthenticatedLambdaRequest<CreateRecipeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateRecipeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateRecipeRequest unauthenticatedRequest = input.fromBody(CreateRecipeRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateRecipeRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withRecipeName(unauthenticatedRequest.getRecipeName())
                                    .withIngredients(unauthenticatedRequest.getIngredients())
                                    .withRecipeSteps(unauthenticatedRequest.getRecipeSteps())
                                    .withServings(unauthenticatedRequest.getServings())
                                    .withCalories(unauthenticatedRequest.getCalories())
                                    .build());

                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideCreateRecipeActivity().handleRequest(request)
        );
    }
}
