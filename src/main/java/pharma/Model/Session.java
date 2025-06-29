package pharma.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.*;

public class Session {
   @JsonProperty("sessions")
    private List<Sessions> sessions;

    public Session() {

    }

    public Session(List<Sessions> sessions) {
        this.sessions = sessions;
    }

    public List<Sessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<Sessions> sessions) {
        this.sessions = sessions;
    }

    public static  class AuthenticationFactor{
        @JsonProperty("last_authenticated_at")
        private  String last_authenticated_at;

        public AuthenticationFactor() {
        }

        public AuthenticationFactor(String last_authenticated_at) {
            this.last_authenticated_at = last_authenticated_at;
        }

        public String getLast_authenticated_at() {
            return last_authenticated_at;
        }

        public void setLast_authenticated_at(String last_authenticated_at) {
            this.last_authenticated_at = last_authenticated_at;
        }
    }

    public   static  class Sessions{
        @JsonProperty("authentication_factors")
        private List<AuthenticationFactor> authenticationFactor;
        public Sessions() {

        }

        public Sessions(List<AuthenticationFactor> authenticationFactor) {
            this.authenticationFactor = authenticationFactor;
        }

        public List<AuthenticationFactor> getAuthenticationFactor() {
            return authenticationFactor;
        }

        public void setAuthenticationFactor(List<AuthenticationFactor> authenticationFactor) {
            this.authenticationFactor = authenticationFactor;
        }

        @Override
        public String toString() {
            return "Sessions{" +
                    "authenticationFactor=" + authenticationFactor +
                    '}';
        }
    }
}


