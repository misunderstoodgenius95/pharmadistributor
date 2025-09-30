package pharma.security.crypto;

import org.jetbrains.annotations.TestOnly;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

public class Chacha20 {
   private static String key="8ea690edf585aaeb422a67410aff3a46ab996418db3708ec526587d092ec7e69";

    public static Optional<byte[]> encrypt(byte[] plaintext,byte[] nonce){
        byte[] b_key=hexToByte(key);
        Cipher cipher=null;
        SecretKeySpec secretKeySpec=new SecretKeySpec(b_key,"ChaCha20");
        IvParameterSpec spec=new IvParameterSpec(nonce);
        try {
            cipher=Cipher.getInstance("ChaCha20-Poly1305");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,spec);
              return Optional.of(cipher.doFinal(plaintext));




        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
           e.printStackTrace();
        }


        return Optional.empty();
    }

    public static Optional<String> encryptString(String plaintext, byte[] nonce){
        Optional<byte[]>optionalBytes=encrypt(plaintext.getBytes(StandardCharsets.UTF_8),nonce);
        return optionalBytes.map(bytes -> Base64.getEncoder().encodeToString(bytes));


    }
    @TestOnly
   public static byte[] hexToByte(String  hex){
        hex=hex.replace("//s+","");
        int length=hex.length();


        byte[] bytes=new  byte[length/2];
        for(int i=0;i<length;i+=2){
            String byteString=hex.substring(i,i+2);
            bytes[i/2]=(byte)Integer.parseInt(byteString,16);
        }
        return bytes;
    }



    public static String byteToHex(byte[] bytes){
        StringBuilder stringBuilder=new StringBuilder();
        for(byte b:bytes){
            stringBuilder.append(String.format("%02x",b));

        }
        return  stringBuilder.toString();


    }

    public  static  byte[] generateNonce(){

        byte[] nonce=new byte[12];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
    public static Optional<byte[]> decrypt(byte[] ciphertext,byte[] nonce){
         byte[] key_bytes=hexToByte(key);
        SecretKeySpec secretKeySpec=new SecretKeySpec(key_bytes,"ChaCha20");
        IvParameterSpec ivParameterSpec=new IvParameterSpec(nonce);
        Cipher cipher=null;
        try {
            cipher=Cipher.getInstance("ChaCha20-Poly1305");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
             return Optional.of(cipher.doFinal(ciphertext));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException e) {
           e.printStackTrace();

        }
        return Optional.empty();
    }
    public  static String decryptedString(byte[] decrypted){
       return new String(decrypted, StandardCharsets.UTF_8);


    }



}
