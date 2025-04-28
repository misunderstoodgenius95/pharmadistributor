package pharma.config.auth;

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
