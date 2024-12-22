package pharma.security;

import org.json.JSONObject;
import pharma.Storage.FileConfig;

import java.net.http.HttpClient;

public class Authentication {
    private FileConfig fileConfig;
    private String username;
    private String password;
    private HttpClient httpclient;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
        httpclient = HttpClient.newHttpClient();
  /*  fileConfig=new FileConfig("auth.properties");

    }
    public boolean authenticate(String username, String password) {
        fileConfig.setProperties();

    }
    private  String build_body(){




    }

   */


    }
}
