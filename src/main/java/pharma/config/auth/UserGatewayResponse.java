package pharma.config.auth;

public class UserGatewayResponse {
    private  String body;
    private  int status;

    public UserGatewayResponse(String body, int status) {
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
