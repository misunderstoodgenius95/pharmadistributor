package pharma.test2;

import org.postgresql.largeobject.LargeObject;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Identify {
    private static Logger logger= Logger.getLogger(Identify.class.getName());
   private String email;
    private ThreadServerManager serverManager;
    private boolean is_joined;

    public Identify(String email, ThreadServerManager serverManager) {
        this.email = email;
        this.serverManager = serverManager;
        is_joined=false;
    }

    public String getEmail() {
        return email;
    }

    public ThreadServerManager getServerManager() {
        return serverManager;
    }

    public boolean isIs_joined() {
        return is_joined;
    }

    public void setIs_joined(boolean is_joined) {
        this.is_joined = is_joined;
    }
    public static void execute_ispharmacist(){
        Executors.newSingleThreadExecutor().submit(()->
        logger.info("size p: "+ActiveClient.getPharmacist_map().size()));

    }

}
