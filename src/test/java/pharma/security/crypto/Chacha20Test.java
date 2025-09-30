package pharma.security.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Chacha20Test {

    @Test
    void hexToByte() {
        String hex="8ea690edf585aaeb422a67410aff3a46ab996418db3708ec526587d092ec7e69";
        byte[] bytes=Chacha20.hexToByte(hex);
        String hex_actual=Chacha20.byteToHex(bytes);
        Assertions.assertEquals(hex,hex_actual);
    }
public byte[] conversion(int[] nonce){
    byte[] bytes=new byte[nonce.length];
    for(int i=0;i< nonce.length;i++){
        bytes[i]=(byte) nonce[i];

    }
    return  bytes;
}
    @Test
    void encrypt() {
        String plaintext="Ciao";
        int[] nonce={121, 119, -85, -8, -78, 126, 107, 78, 17, 119, -75, 4};
        byte[] noncebytes=conversion(nonce);
        byte[] plaintextBytes= plaintext.getBytes(StandardCharsets.UTF_8);
        Optional<byte[]> optional_byte=Chacha20.encrypt(plaintextBytes,noncebytes);
        byte[] decrypted_byted=Chacha20.decrypt(optional_byte.get(),noncebytes).get();
       Assertions.assertEquals(plaintext,Chacha20.decryptedString(decrypted_byted));

    }

    @Test
    void generateNonce() {
        String string_array=Arrays.toString(Chacha20.generateNonce());
        String[]splitted=string_array.split(" ");
        System.out.println(string_array);
      //  Assertions.assertEquals(12,splitted.length);
    }
}