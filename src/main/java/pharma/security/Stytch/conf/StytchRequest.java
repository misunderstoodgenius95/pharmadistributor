package pharma.security.Stytch.conf;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;

public class StytchRequest {


    private  String encodedAuth;

    public StytchRequest(String encodedAuth) {
        this.encodedAuth = encodedAuth;
    }

    public HttpRequest auth_request(String uri, JSONObject credentials_json){
        return HttpRequest.newBuilder(URI.create(uri)).
                setHeader("Content-Type", "application/json").
                setHeader("Authorization", "Basic " + encodedAuth).
                POST(HttpRequest.BodyPublishers.ofString(credentials_json.toString())).build();

    }

}
