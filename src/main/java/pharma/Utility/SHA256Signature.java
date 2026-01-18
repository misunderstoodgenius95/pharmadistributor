package pharma.Utility;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SHA256Signature {
    private static final  String SIGNATURE="SHA256withRSA";

    private static byte[] pemToDer(String pem) {
        return Base64.getDecoder().decode(
                pem.replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s+", "")  // removes \n, \r, spaces
        );
    }

    public static boolean verifySignature(
            String message,
            String signatureBase64,
            String publicKeyPem) throws Exception {

        byte[] publicKeyDer = pemToDer(publicKeyPem);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyDer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(message.getBytes("UTF-8"));

        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

        return sig.verify(signatureBytes);
    }






}
