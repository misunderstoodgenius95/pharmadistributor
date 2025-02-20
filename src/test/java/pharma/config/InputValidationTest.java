package pharma.config;


import net.jodah.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class InputValidationTest  {


    @Test
    void validate_email() {
        //Valid Test email
        Assertions.assertTrue(InputValidation.validate_email("user@example.com")); //format email
        Assertions.assertTrue(InputValidation.validate_email("user.sub@amazon.example.com")); //subdomain and dot local part
        Assertions.assertTrue(InputValidation.validate_email("user@amazon.example.com")); // subdomain
        Assertions.assertTrue(InputValidation.validate_email("user.sub@example.com")); // dot domain
        //Invalid Test email



    }
    @Test
    void invalid_email() {
        Assertions.assertFalse(InputValidation.validate_email("ettore.bassexample.com"));
        Assertions.assertFalse(InputValidation.validate_email("@example.com"));

        //Invalid email format
        Assertions.assertFalse(InputValidation.validate_email(""));// Input empty

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            InputValidation.validate_email(null);
        });


    }

    @Test
    void validate_password() {
        //Valid password

        Assertions.assertTrue(InputValidation.validate_password("@5&17Vhm5QGp"));

        //Invalid password
        Assertions.assertFalse(InputValidation.validate_password("")); // empty
        Assertions.assertThrows(IllegalArgumentException.class,()->{ //null
            InputValidation.validate_password(null);
        });
        Assertions.assertFalse(InputValidation.validate_password("abcde")); //Less than 12
        Assertions.assertFalse(InputValidation.validate_password("abcedghilm11@")); //No Upper Case
        Assertions.assertFalse(InputValidation.validate_password("aMbcedghilm11")); //No special Character
        Assertions.assertFalse(InputValidation.validate_password("aMbcedghilmiii%"));//No digit
    }

    @Test
    void valid_audience() {
        Assertions.assertTrue(InputValidation.validate_audience("https://example.com"));// only domain
        Assertions.assertTrue(InputValidation.validate_audience("https://example.com/pharma")); //domain plus patch
    }
    @Test
    void invalid_audience() {
        Assertions.assertFalse(InputValidation.validate_audience("http://example.com"));// invalid http domain
        Assertions.assertFalse(InputValidation.validate_audience("https://")); //missing domain
        Assertions.assertFalse(InputValidation.validate_audience("https://example.")); // missing TLD
    }


    @Test
    void validate_p_iva() {
        Assertions.assertTrue(InputValidation.validate_p_iva("IT12345555555"));
    }
    @Test
    void invalidate_p_iva() {
        Assertions.assertTrue(InputValidation.validate_p_iva("11111"));
    }

    @Test
    void Validvalidate_double_digit() {
        Assertions.assertTrue(InputValidation.validate_double_digit("10.50"));
    }
    @Test
    void InValidvalidate_double_digit() {
        Assertions.assertFalse(InputValidation.validate_double_digit("1050"));
    }

    @Test
    void Validvalidate_lotto_code() {
        Assertions.assertTrue(InputValidation.validate_lotto_code("b8188j"));
        Assertions.assertTrue(InputValidation.validate_lotto_code("M20773"));
    }
    @Test
    void InValidvalidate_lotto_code() {
        Assertions.assertFalse(InputValidation.validate_lotto_code("b818*8j"));
    }


    @Test
    void get_validation() {
     //  Assertions.assertTrue( InputValidation.get_validation("Lotto_code","M20773"));
        Assertions.assertTrue( InputValidation.get_validation("Double_Digit","10.50"));

    }


}

