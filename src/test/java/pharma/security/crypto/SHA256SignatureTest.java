package pharma.security.crypto;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Utility.SHA256Signature;

import java.security.NoSuchAlgorithmException;

class SHA256SignatureTest {


    @Test
    void validSignature() {

    }

    @Test
    void generatePublicKey() throws NoSuchAlgorithmException {
    String pu="\"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzOPvF83+F8FLYn7FcmU+\\nDaME5uKsXBie6/gFMpEmVTx4et7tiLdqwcIdzZVK/b9Yr8zQRghTs3xMCpVtXceh\\nCTwP4xP7fE5OsGBRH97i6luwPmtE7wN42I7ikAKeREIY0hIQfjbKGTFDsHj1hP7/\\neSFCRaILUMfBEmaRxaRG0xktNKt5wfrxFrxx8+sZCuBu478rrNL2wRBWsXTYuSmn\\ncOq0JO4KdWag3ZSK65UYEHFgUhzviHcurZH3hX//7VvsdI7wO/I3YVMNJ8WHEJuP\\nmyHUaZQ6kZDKJwJqhLJk4LIiU+tvqPAElCqwWXPntAxl+lnj7q5mnMX5+rQqYm+t\\n+QIDAQAB\\n-----END PUBLIC KEY-----";
;


    }

    @Test
    void generateStringToByte() {
    }

    @Test
    void loadPublicKey() throws Exception {
    String json="{\"signature\":\"y/pRxpPRdKFnkIkfn+TYvwZ0zOrm0egi/Ge9B4NKrzA9P2eKwt/7/wsCywv3s3FtFZYjYR05/pDdzvPZDfXVWPc4HUbF9A9kZLstysQ29AABFnxFI/eQK5r2LbrthWsi4quOtcCltfFgcAB/Icq6r2v9wMTbAcZN50jOXUP2nAlnt4H4QjtWFWUj06OqB/38c7+atYSY/KImEo23+TmNuELKrgrNPUJ65yyhq6N1cYNE8LAQJmw7Iy2wkZukYpsw0Q/tKBQcF/cC2e8bpAhV1X21SAoGJRzcEc05dUzyG0tCsJrBCTR68N8nSBWy+dHK8V0nayitABLM7R0qonsP7A==\",\"publicKey\":\"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzOPvF83+F8FLYn7FcmU+\\nDaME5uKsXBie6/gFMpEmVTx4et7tiLdqwcIdzZVK/b9Yr8zQRghTs3xMCpVtXceh\\nCTwP4xP7fE5OsGBRH97i6luwPmtE7wN42I7ikAKeREIY0hIQfjbKGTFDsHj1hP7/\\neSFCRaILUMfBEmaRxaRG0xktNKt5wfrxFrxx8+sZCuBu478rrNL2wRBWsXTYuSmn\\ncOq0JO4KdWag3ZSK65UYEHFgUhzviHcurZH3hX//7VvsdI7wO/I3YVMNJ8WHEJuP\\nmyHUaZQ6kZDKJwJqhLJk4LIiU+tvqPAElCqwWXPntAxl+lnj7q5mnMX5+rQqYm+t\\n+QIDAQAB\\n-----END PUBLIC KEY-----\\n\",\"type\":\"serverAuth\",\"message\":\"Hello\"}";
        JSONObject jsonObject=new JSONObject(json);
        String publicKey=jsonObject.getString("publicKey");
        String message=jsonObject.getString("message");
        String signature=jsonObject.getString("signature");
        Assertions.assertTrue(SHA256Signature.verifySignature(message,signature,publicKey));


    }
}