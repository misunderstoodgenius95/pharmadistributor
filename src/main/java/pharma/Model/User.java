package pharma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class User {
    private List<Results> results;
    @JsonIgnore
    private int status;
    @JsonProperty("trusted_metadata")
    private TrustedMetadata trustedMetadata;

    public int getStatus() {
        return status;
    }

    public TrustedMetadata getTrustedMetadata() {
        return trustedMetadata;
    }

    public void setTrustedMetadata(TrustedMetadata trustedMetadata) {
        this.trustedMetadata = trustedMetadata;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User() {

    }


    public User(List<Results> results) {
        this.results = results;
    }



    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static  class Emails{
        private String email;

        public Emails(String email) {
            this.email = email;
        }

        public Emails() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public static  class TrustedMetadata{
            private String role;
            private boolean is_enable;

        public TrustedMetadata(String role, boolean is_enable) {
            this.role = role;
            this.is_enable = is_enable;
        }

        public TrustedMetadata() {

        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public boolean isIs_enable() {
            return is_enable;
        }

        public void setIs_enable(boolean is_enable) {
            this.is_enable = is_enable;
        }
    }
    public static class  Results{
        private  String user_id;
        private  List<Emails> emails;
        private Instant last_access;
        @JsonProperty("trusted_metadata")
        private  TrustedMetadata trustedMetadata;
        private List<String> roles;

        public List<String> getRoles() {
            return roles;
        }


        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
        public Results() {
        }

        public Results(String user_id, List<Emails> emails, TrustedMetadata trustedMetadata) {
            this.user_id = user_id;
            this.emails = emails;
            this.trustedMetadata = trustedMetadata;
        }
        public Instant getLast_access() {
            return last_access;
        }

        public void setLast_access(Instant last_access) {
            this.last_access = last_access;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<Emails> getEmails() {
            return emails;
        }

        public void setEmails(List<Emails> emails) {
            this.emails = emails;
        }

        public TrustedMetadata getTrustedMetadata() {
            return trustedMetadata;
        }

        public void setTrustedMetadata(TrustedMetadata trustedMetadata) {
            this.trustedMetadata = trustedMetadata;
        }
    }

}
