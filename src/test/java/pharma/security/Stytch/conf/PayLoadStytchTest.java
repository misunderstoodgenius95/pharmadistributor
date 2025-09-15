package pharma.security.Stytch.conf;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PayLoadStytchTest {
    @Test
    void buildSearchByEmail() {
        String query = "{"
                + "\"limit\": 1000,"
                + "\"cursor\": \"\","
                + "\"query\": {"
                + "\"operator\": \"AND\","
                + "\"operands\": ["
                + "{"
                + "\"filter_name\": \"email_address\","
                + "\"filter_value\": [\"user@example.com\"]"
                + "}"
                + "]"
                + "}"
                + "}";
        String actual=PayLoadStytch.buildSearchByEmail("user@example.com");
            Assertions.assertEquals(new JSONObject(query).toString(),actual);
        System.out.println(actual);

    }

    @Test
    void create_user_pharmacist() {
        String actual=PayLoadStytch.create_user_pharmacist("user@example.com","B!jdH6a5N$g1","pharmacist","luca","me",1);
        System.out.println(actual);
    }

    @Nested

    class  GetUser {
    @Test
        void ValidGetUser(){
        Assertions.assertEquals(new JSONObject().put("email","user@example.com").put("password","B!jdH6a5N$g1").put("session_duration_minutes",240).toString(),
                PayLoadStytch.get_user("user@example.com","B!jdH6a5N$g1"));
    }
    @Test
        void invalidGetUserNUll(){
        Assertions.assertThrows(IllegalArgumentException.class,()->PayLoadStytch.get_user(null,"aa"));
    }
        @Test
        void invalidGetUserEmpty(){
            Assertions.assertThrows(IllegalArgumentException.class,()->PayLoadStytch.get_user("","aa"));
        }



    }


    @Nested
    class  CreateUser {
        @Test
        void ValidCreate_user() {
            JSONObject jsonObject=new JSONObject().put("email","user@example.com").
                    put("password","B!jdH6a5N$g1").put("trusted_metadata",new JSONObject().put("role","seller")).put("name",new JSONObject().put("first_name","luca").put("last_name",
                    "me")).put("session_duration_minutes",240);


            String actual=PayLoadStytch.create_user("user@example.com","B!jdH6a5N$g1","luca","me");
            Assertions.assertEquals(jsonObject.toString(),actual);

        }
        @Test
        void InvalidCreateUserNull(){



            Assertions.assertThrows(IllegalArgumentException.class,()->
                    PayLoadStytch.create_user(null,"1234567899","luca","me"));

        }
        @Test
        void InvalidCreateUserEmpty(){



            Assertions.assertThrows(IllegalArgumentException.class,()->
                    PayLoadStytch.create_user("","1234567899","luca","me"));

        }

    }


    @Test
    void buildAuthPayload_token() {
    }

    @Test
    void buildAuthPayload_jwt_token() {
    }
}