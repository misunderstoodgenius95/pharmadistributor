package pharma.config;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.accessibility.AccessibleStateSet;


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
    void validate_stytch_user_id() {

        Assertions.assertTrue(InputValidation.validate_stytch_user_id("user-test-16d9ba61-97a1-4ba4-9720-b03761dc50c6"));
    }

    @Test
    void validate_lng() {
       Assertions.assertTrue(InputValidation.validate_lng("-39.09529473"));
    }

    @Test
    void validate_lat() {
        Assertions.assertTrue(InputValidation.validate_lat("-18.818886984"));
    }

    @Nested
     class ValidatePassword {

        @Test
        void Validate_password() {
            //Valid password

            //Assertions.assertTrue(InputValidation.validate_password("@5&17Vhm5QGp"));
            Assertions.assertTrue(InputValidation.validate_password("B!jdH6a5N$g1"));
        }
        @Test
        void InvalidEmpty() {
            //Invalid password
            Assertions.assertFalse(InputValidation.validate_password(""));
        }
        @Test
        void InvalidNull() {// empty
            Assertions.assertThrows(IllegalArgumentException.class, () -> { //null
                InputValidation.validate_password(null);
            });
        }
        @ParameterizedTest
        @CsvSource({"abcde","abcedghilm11@","aMbcedghilm11","aMbcedghilmiii%"})
        public  void InvalidFalse(String input){

            Assertions.assertFalse(InputValidation.validate_password(input)); //Less than 12

        }


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


    @Test
    void validate_digit() {
        Assertions.assertTrue(InputValidation.validate_digit("11a0"));
    }

    @Test
    void filled_text() {
        Assertions.assertTrue(InputValidation.filled_text("Ansiolitici"));
    }

    @Test
    void ValidatePassword(){
        Assertions.assertTrue(InputValidation.validate_password("&6hF%@&yvBE"));
    }

    @Test
    void validate_cap() {
        Assertions.assertTrue(InputValidation.validate_cap("98074"));
    }
    @Nested
        class Stytch_project_id {
            @Test
        void Valid_validate_stytch_project_id() {
            Assertions.assertTrue(InputValidation.validate_stytch_project_id("project-test-4acd22c0-ef21-4294-97b1-38d96e7a77c2"));
        }     @Test
        void Invalid_validate_stytch_project_id() {
            Assertions.assertThrows(IllegalArgumentException.class,()->InputValidation.validate_stytch_project_id(null));
        }




        }
        @Nested
        class ValidateStytchSecret {
            @ParameterizedTest
            @CsvSource({"secret-live-BWW4Lql0pdxl7JqobBndAhqPzIG7BzQlC5U=",
            "secret-test-ed1zX4Tr-3_zWFncQ_yEkm6o3yzeyJ8vr8Y="})
            void Validvalidate_stytch_secret_key(String input) {
                Assertions.assertTrue(InputValidation.validate_stytch_secret_key(input));
            }
            @ParameterizedTest
            @NullSource
            void InValidvalidate_stytch_secret_keyNull(String input) {
                Assertions.assertThrows(IllegalArgumentException.class,()->InputValidation.validate_stytch_secret_key(input));
            }
            @ParameterizedTest
            @CsvSource({"secret-live-","secret-live-11111"})
            void InValidvalidate_stytch_secret_key(String input) {
                Assertions.assertFalse(InputValidation.validate_stytch_secret_key(input));
            }



        }
}

