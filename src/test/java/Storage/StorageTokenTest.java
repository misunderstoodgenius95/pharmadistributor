package Storage;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTokenTest {

    @Test
    void store_token() {
    }

    @Test
    void get_token() {
        String token=StorageToken.get_token();
        assertNotNull(token);

assertEquals(token,"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFuVmtvcFMyVWREVm01LXVEWkZwSCJ9.eyJyb2xlIjoicHVyY2hhc2UiLCJpc3MiOiJodHRwczovL2Rldi1tZDAwM3N5ZThsYnM4azdnLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NzQ5ZmJkM2I3Y2IzYTJhOTJkNDA2NjEiLCJhdWQiOiJodHRwczovL2Rpc3Ryb2FwaS5jb20iLCJpYXQiOjE3MzM1MTA4MTAsImV4cCI6MTczMzU5NzIxMCwiZ3R5IjoicGFzc3dvcmQiLCJhenAiOiJwMTRwWnpib0tKZUNjSWpmbWtiM255VEpvRDE0bWYxciIsInBlcm1pc3Npb25zIjpbIndyaXRlOmFkZF9waGFyYW1hIl19.XMQ4d8M9Gy6TwZd5vlsg7dUJdyahmISiF-3PNi2UZTRIFaur4zmt_1dVq2rDLEWTVplIPKH1gbFGj_vjldrpJlJoYzJDrC_YUZQZlFQ3qr-pp2yOTNRkiyS9LUJF1V3Xl8BHbB-LgpE91I18-pSN63LpjZm5HsuVggVQ1q3XsHTHLFpJE871FL4OYQ49CnZXjuVtPEt7ykLCDEjhKMIVflti3mehWIrrDgZ_dGqEv94tOWcff3diu-dVl4TuqYDWQcN0Heu8ZvXaxYvBS_sbTEtZs1Iq9vMB_45InvGbn03kZpJS5TZTnX-5QC-MjL6JcB8PilKnXvFkoIhwxXeweg");

    }
}