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
                                    .withRecipeName(unauthenticatedRequest.getRecipeName())
                                    .withServings(unauthenticatedRequest.getServings())
                                    .withIngredients(unauthenticatedRequest.getIngredients())
//                                    .withRecipeSteps(unauthenticatedRequest.getRecipeSteps())
                                    .withCalories(unauthenticatedRequest.getCalories())
                                    .withUserId(claims.get("email"))
                                    .build());

                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideCreateRecipeActivity().handleRequest(request)
        );
    }
}
