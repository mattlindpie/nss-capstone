package nss.capstone.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import nss.capstone.activity.requests.DeleteRecipeRequest;
import nss.capstone.activity.results.DeleteRecipeResult;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DeleteRecipeLambda
        extends LambdaActivityRunner<DeleteRecipeRequest, DeleteRecipeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteRecipeRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteRecipeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteRecipeRequest unauthenticatedRequest = input.fromPath(path -> DeleteRecipeRequest.builder()
                            .withRecipeName(URLDecoder.decode(path.get("recipeName"), StandardCharsets.UTF_8))
                            .build());

                    return input.fromUserClaims(claims ->
                            DeleteRecipeRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withRecipeName(unauthenticatedRequest.getRecipeName())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteRecipeActivity().handleRequest(request)
        );
    }
}

