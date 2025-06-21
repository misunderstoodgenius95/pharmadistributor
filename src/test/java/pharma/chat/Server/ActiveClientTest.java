package pharma.chat.Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.List;

class ActiveClientTest {
   /*
    @Test
    void wait_pharmacist() {
*//*
        ActiveClient.setSeller_map("luca@example.com",new ThreadServerManager());
        ActiveClient.setPharmacist_map("debby@example.com",new ThreadServerManager());
        ActiveClient.setPharmacist_map("giorgia@example.com",new ThreadServerManager());
        ActiveClient.setJoinChat("debby@example.com","luca@example.com");
        System.out.println(ActiveClient.wait_pharmacist());
*//*

    }

    @Test
    void Valid_is_chat_empty() {

        ActiveClient.setJoinChat("username@example.com", "pinco@example.com");
        Assertions.assertFalse(ActiveClient.is_chat_empty());
    }

    @Test
    void ValidsetSeller_map() {

        Assertions.assertTrue(ActiveClient.setSeller_map("pinco@example.com",new ThreadServerManager()));

    }
    @Test
    void InvalidSellerMap(){
        ActiveClient.setSeller_map("pinco@example.com",new ThreadServerManager());
        Assertions.assertFalse(ActiveClient.setSeller_map("pinco@example.com",new ThreadServerManager()));

    }

    @Test
    void removePharmacistSeller() {
      *//*  ActiveClient.setJoinChat("debby@example.com","icegiuly@example.com");
        ActiveClient.setJoinChat("eleonora@example.com","icegiuly@example");
        ActiveClient.removePharmacistSellerIntoChat("debby@example.com");
        List<String> list = ActiveClient.getListPharmacistBySeller("icegiuly@example.com");
        Assertions.assertFalse(list.contains("debby@example.com"));*//*


    }

    @Nested
    class RandomSeller {
        @Test
        void Invalidget_random_seller() {
            Assertions.assertThrows(RuntimeException.class, ActiveClient::get_random_seller);



        }
        @Test
        void Valid_random_seller(){
            ActiveClient.setSeller_map("pinco@example.com",null);
            ActiveClient.setSeller_map("pallino@example.com",null);
            System.out.println(ActiveClient.get_random_seller());


        }
    }

    @Nested
    class JoinChat {
        @Test
        void ValidJoinOneElement() {
            ActiveClient.setJoinChat("username@example.com", "pinco@example.com");
            List<String> list = ActiveClient.getListPharmacistBySeller("pinco@example.com");
            Assertions.assertEquals(1, list.size());
        }
        @Test
        void ValidAppendNewElement() {
            ActiveClient.setJoinChat("username@example.com", "pinco@example.com");
            ActiveClient.setJoinChat("debby@example.com","pinco@example.com");
            Assertions.assertEquals(2,ActiveClient.getListPharmacistBySeller("pinco@example.com").size());
        }





    }
    @Test
    void ValidComponentTesting(){
        String email="seller@example.com";
     ActiveClient.setPharmacist_map("debby@example.com",null);
        ActiveClient.setPharmacist_map("luca@example.com",null);
        if(!ActiveClient.wait_pharmacist().isEmpty()){
            String pharmacist=ActiveClient.wait_pharmacist().getFirst();

            ActiveClient.setJoinChat(pharmacist,email);

        }

        Assertions.assertEquals(1,ActiveClient.getListPharmacistBySeller(email).size());





    }*/
}