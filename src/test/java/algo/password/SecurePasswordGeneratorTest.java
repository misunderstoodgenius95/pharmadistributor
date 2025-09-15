package algo.password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordGeneratorTest {

    @Test
    public void test(){
        System.out.println(SecurePasswordGenerator.generatePassword(12));
    }

}