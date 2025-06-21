package pharma.config.auth;

import pharma.Model.FieldData;

public class UserServiceResponse {
    private  String body;
    private  int status;

    public UserServiceResponse(String body, int status) {
        this.body = body;
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public int getStatus() {
        return status;
    }
}
