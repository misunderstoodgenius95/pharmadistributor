package pharma.security.Stytch.conf;

import com.auth0.net.client.HttpRequestBody;
import org.json.HTTP;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;

public class StytchRequest {


    private String encodedAuth;

    public StytchRequest(String encodedAuth) {

        if (encodedAuth == null || encodedAuth.trim().isEmpty()) {
            throw new IllegalArgumentException("Encoded auth cannot be null or empty");
        }
        this.encodedAuth = encodedAuth;
    }

    public HttpRequest auth_request(String uri, String credentials_json) {

        if (uri == null || uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be null or empty");
        }
        if (credentials_json == null || credentials_json.trim().isEmpty()) {
            throw new IllegalArgumentException("Credentials JSON cannot be null or empty");
        }

        return HttpRequest.newBuilder(URI.create(uri)).
                setHeader("Content-Type", "application/json").
                setHeader("Authorization", "Basic " + encodedAuth).
                POST(HttpRequest.BodyPublishers.ofString(credentials_json)).build();

    }

    public HttpRequest search_all(String uri, String payload) {

        if (uri == null || uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be null or empty");
        }

        return HttpRequest.newBuilder(URI.create(uri)).setHeader("Content-Type", "application/json").
                setHeader("Authorization", "Basic " + encodedAuth).POST(HttpRequest.BodyPublishers.ofString(payload)).build();

    }

    public HttpRequest search_by_email(String uri, String payload) {
        if (uri == null || uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be null or empty");
        }
        return HttpRequest.newBuilder(URI.create(uri)).setHeader("Content-Type", "application/json").
                setHeader("Authorization", "Basic " + encodedAuth).POST(HttpRequest.BodyPublishers.ofString(payload)).build();

    }


    public HttpRequest update_user(String uri, JSONObject payload) {
        if (uri == null || uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be null or empty");
        }
        if (payload == null) {
            throw new IllegalArgumentException(" Payload cannot be null or empty");

        }

        return basic_request_post(uri).PUT(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    }

    public HttpRequest session_user(String uri, String user_id) {

        if (uri == null || uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be null or empty");
        }
        uri += "?" + "user_id=" + user_id;
        System.out.println(uri);

        return HttpRequest.newBuilder(URI.create(uri)).
                setHeader("Authorization", "Basic " + encodedAuth).GET().build();
    }

    private HttpRequest.Builder basic_request_post(String uri) {

        return HttpRequest.newBuilder(URI.create(uri)).setHeader("Content-Type", "application/json").
                setHeader("Authorization", "Basic " + encodedAuth);
    }

    public HttpRequest reset_password_start_body(String uri, JSONObject jsonObject) {

        return basic_request_post(uri).POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();

    }

}



