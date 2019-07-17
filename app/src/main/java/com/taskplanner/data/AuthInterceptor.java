package com.taskplanner.data;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String tokenId = FirebaseAuth.getInstance().getCurrentUser().getIdToken(false).getResult().getToken();
        Request original = chain.request();
        Request request = original.newBuilder()
                //.header("X-Firebase-Auth", "serega_mem")
                .header("X-Firebase-Auth", tokenId != null ? tokenId : "")
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);

        return response;
    }
}
