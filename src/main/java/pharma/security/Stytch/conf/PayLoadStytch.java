package pharma.security.Stytch.conf;

import org.json.JSONObject;

public class PayLoadStytch {
    public static JSONObject get_user(String email, String password) {
        return new JSONObject()
                .put("email", email)
                .put("password", password).put("session_duration_minutes", 240);

    }
        public static JSONObject create_user(String email, String password,String role,String first_name, String surname ) {
            return get_user(email,password).put("trusted_metadata",new JSONObject().put("role",role))
                    .put("name",new JSONObject().put("first_name",first_name).put("last_name",surname));
        }

    public static JSONObject buildAuthPayload_token(String token) {
        return new JSONObject()
                .put("session_token", token).put("session_duration_minutes", 240);
    }

    public static JSONObject buildAuthPayload_jwt_token(String token) {
        return new JSONObject()
                .put("session_jwt", token).put("session_duration_minutes", 240);
    }

}
