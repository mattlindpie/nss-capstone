package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.GetRecipeRequest;
import nss.capstone.activity.results.GetRecipeResult;

import java.nio.charset.StandardCharsets;

public class GetRecipeLambda
        extends LambdaActivityRunner<GetRecipeRequest, GetRecipeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetRecipeRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetRecipeRequest> input, Context context) {
        return super.runActivity(
                () -> {

                    GetRecipeRequest unauthenticatedRequest = input.fromPath(path -> GetRecipeRequest.builder()
                            .withRecipeName(java.net.URLDecoder.decode(path.get("recipeName"), StandardCharsets.UTF_8))
                            .build());

                    return input.fromUserClaims(claims ->
                            GetRecipeRequest.builder()
                                    .withRecipeName(unauthenticatedRequest.getRecipeName())
                                    .withUserId(claims.get("email"))
                                    .build());

                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetRecipeActivity().handleRequest(request)
        );
    }


}
