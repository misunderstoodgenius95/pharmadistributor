
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import security.TokenUtility;

public class JwtExtractTest {

    private static String token;

    @BeforeAll
    static void setUpAll() {
        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFuVmtvcFMyVWREVm01LXVEWkZwSCJ9.eyJyb2xlIjoicHVyY2hhc2UiLCJpc3MiOiJodHRwczovL2Rldi1tZDAwM3N5ZThsYnM4azdnLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NzQ5ZmJkM2I3Y2IzYTJhOTJkNDA2NjEiLCJhdWQiOiJodHRwczovL2Rpc3Ryb2FwaS5jb20iLCJpYXQiOjE3MzM0ODI3NTksImV4cCI6MTczMzU2OTE1OSwiZ3R5IjoicGFzc3dvcmQiLCJhenAiOiJwMTRwWnpib0tKZUNjSWpmbWtiM255VEpvRDE0bWYxciIsInBlcm1pc3Npb25zIjpbIndyaXRlOmFkZF9waGFyYW1hIl19.A67uusCDNj2Jq0H0XgBclvuDitCqT3RXZO9TwYtrPDWWRkp6tyEowaTLnjREK3vvOBdtgY40QdOSJAh5aeowyQs1s2q6vlDEZt-Cg3CYsSw6dXOsJfwp9Zg4oSy303wEPh2h2pZ1HLZQVXa5dEZ4ZLojXhSL9uCy2LS0TiX2EHyJNIXspynZcN7TMmaE4fJ_VtuDpxQUF_-aVDXHVQn-41JM1kpEqHHQvdHhIo3wQ-ZFTbIMfgxDjnz-OSeoOSsRGJ2R2c334y7yIJHUcm_pImikmRbWBUw6HOnPRyzzn-MSw-Z86Pr-m_vciF4tuzWwR66Q-WAH0eJLVpUGbPoaxQ";
    }

    @Test

    public void check_roles() {
        String purchase = "purchase";
        Assertions.assertEquals("purchase", TokenUtility.extractRole(token).replace("\"", ""));
    }


    @Test
    void check_permissions() {

 boolean actual= TokenUtility.check_permission(token,"write","add_pharma");
Assertions.assertTrue(actual);
    }


@Test
    public void extract_permission(){

       String[] permissions= TokenUtility.extractPermission(token);
        for(String permission:permissions){
            System.out.println(permission);
        }

    }




}