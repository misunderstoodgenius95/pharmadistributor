package algo.password;

import algoWarehouse.password.SecurePasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SecurePasswordGeneratorTest {

    @Test
    public void test() {
        String password=SecurePasswordGenerator.generatePassword(12);
        org.assertj.core.api.Assertions.assertThat(password.length()).isGreaterThanOrEqualTo(12);


    }
}