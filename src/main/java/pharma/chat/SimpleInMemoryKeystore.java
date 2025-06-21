package pharma.chat;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;

public class SimpleInMemoryKeystore {

    /**
     * Step 1: Create keystore in memory (no files!)
     */
    public static KeyStore createKeystore() throws Exception {
        // Create new empty keystore
        KeyStore keystore = KeyStore.getInstance("JKS");

        // Load empty keystore (null = no file to read from)
        keystore.load(null, "password".toCharArray());

        System.out.println("✅ Empty keystore created in memory");
        return keystore;
    }

    /**
     * Step 2: Generate keys and add to keystore
     */
    public static void addKeysToKeystore(KeyStore keystore) throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        System.out.println("✅ RSA key pair generated");

        // Create simple certificate (for demo only)
        Certificate cert = new DemoCertificate(keyPair.getPublic());

        // Add private key + certificate to keystore
        keystore.setKeyEntry(
                "mykey",                        // key name
                keyPair.getPrivate(),          // private key
                "password".toCharArray(),      // key password
                new Certificate[]{cert}        // certificate
        );

        System.out.println("✅ Keys added to keystore");
    }

    /**
     * Step 3: Convert keystore to InputStream
     */
    public static InputStream keystoreToStream(KeyStore keystore) throws Exception {
        // Create byte array to hold keystore data
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // Save keystore to byte array
        keystore.store(bytes, "password".toCharArray());

        // Convert byte array to InputStream
        InputStream stream = new ByteArrayInputStream(bytes.toByteArray());

        System.out.println("✅ Keystore converted to InputStream");
        return stream;
    }

    /**
     * Step 4: Load keystore from InputStream
     */
    public static KeyStore loadFromStream(InputStream stream) throws Exception {
        // Create new keystore
        KeyStore keystore = KeyStore.getInstance("JKS");

        // Load from InputStream
        keystore.load(stream, "password".toCharArray());

        System.out.println("✅ Keystore loaded from InputStream");
        return keystore;
    }

    /**
     * Step 5: Create SSL context from keystore
     */
    public static SSLContext createSSLContext(KeyStore keystore) throws Exception {
        // Create key manager
        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("SunX509");
        keyFactory.init(keystore, "password".toCharArray());

        // Create trust manager (use same keystore)
        TrustManagerFactory trustFactory = TrustManagerFactory.getInstance("SunX509");
        trustFactory.init(keystore);

        // Create SSL context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyFactory.getKeyManagers(), trustFactory.getTrustManagers(), null);

        System.out.println("✅ SSL context created");
        return sslContext;
    }

    /**
     * Put it all together - create SSL server with in-memory keystore
     */
    public static InputStream generate () throws Exception {
        System.out.println("🚀 Creating SSL server with in-memory keystore...\n");

        // Step 1: Create empty keystore in memory
        KeyStore keystore = createKeystore();

        // Step 2: Add keys to keystore
        addKeysToKeystore(keystore);

        // Step 3: Convert to InputStream (simulating file operations)
        return keystoreToStream(keystore);




    }
}

// =============================================================================
// SIMPLE DEMO CERTIFICATE (For testing only!)
// =============================================================================

/**
 * Very basic certificate class for demo purposes
 * In real apps, use proper certificate generation libraries
 */
class DemoCertificate extends Certificate {
    private final PublicKey publicKey;

    public DemoCertificate(PublicKey publicKey) {
        super("X.509");
        this.publicKey = publicKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getEncoded() {
        return publicKey.getEncoded(); // Simple encoding
    }

    @Override
    public void verify(PublicKey key) {
        // Always valid for demo
    }

    @Override
    public void verify(PublicKey key, String sigProvider) {
        // Always valid for demo
    }

    @Override
    public String toString() {
        return "DemoCertificate[algorithm=" + publicKey.getAlgorithm() + "]";
    }
}



