package pharma.test2;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.postgresql.replication.fluent.AbstractCreateSlotBuilder;

import javax.print.DocFlavor;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.*;

class ActiveClientTest {
    @Mock
    private ThreadServerManager ts;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void Empty_get_random_seller() {
        Optional<String> optional=ActiveClient.get_random_seller();
        Assertions.assertTrue(optional.isEmpty());




    }
    @Test
    void PresentSeller(){

        ActiveClient.setSeller_map("pico",ts);
        Optional<String> seller=ActiveClient.get_random_seller();
        assertTrue(seller.isPresent());



    }
    @Nested
    class SellerJoinChatPharmacist {
        @Test
        void InvalidNoChatPresent_get_SellerJoinChat() {
            Assertions.assertThrows(IllegalArgumentException.class,()->ActiveClient.get_SellerJoinChat("pinco@example.com"));


        }
        @Test
        void get_SellerJoinChat() {
            ActiveClient.setPharmacist_map("pharmacist@example.com",ts);
            ActiveClient.setSeller_map("seller@example.com",ts);
            ActiveClient.setJoinChat("pharmacist@example.com","seller@example.com");
            ActiveClient.setJoinChat("mio@example.com","seller@example.com");
            String seller=ActiveClient.get_SellerJoinChat("pharmacist@example.com");
            Assertions.assertEquals("seller@example.com",seller);


        }
    }

    @Nested
    class RandomSeller {
        @Test
        void Invalidget_random_seller() {
            Optional<String> seller_opt=ActiveClient.get_random_seller();
            Assertions.assertTrue(seller_opt.isEmpty());



        }
        @Test
        void Valid_random_seller(){
            ActiveClient.setSeller_map("pinco@example.com",ts);
            ActiveClient.setSeller_map("pallino@example.com",ts);


            Assertions.assertTrue(Arrays.asList("pinco@example.com","pallino@example.com").contains(ActiveClient.get_random_seller().get()));


        }
    }

    @Nested
    class RandomPharmacist{
        @DisplayName("ChatPharmacist empty and one pharmacist")
        @Test
        public  void ValidOnePharmacistChatEmpty(){
            ActiveClient.setPharmacist_map("pinco",ts);


            Optional<String>  opt=ActiveClient.random_pharmacist();
            Assertions.assertEquals("pinco",opt.get());
        }
        @DisplayName("ValidOneChatPresent")
        @Test
        public void ValidOneChatPresent()  {
            // Create one pharmacist and one seller-> Join this
            ActiveClient.setPharmacist_map("one_p",ts);
            ActiveClient.setSeller_map("one_s",ts);
            ActiveClient.setJoinChat("one_p","one_s");
            // wait pharmacist // return this
            ActiveClient.setPharmacist_map("two_p",ts);
            // new Seller
            ActiveClient.setSeller_map("two_s",ts);
            Assertions.assertEquals("two_p",ActiveClient.random_pharmacist().get());







        }










    }
    @Test
    void me(){


        CopyOnWriteArrayList<Identify> phramacist=new CopyOnWriteArrayList<>();
        phramacist.add(new Identify("pico",ts));
    Identify ids=phramacist.stream().filter(identify -> !identify.isIs_joined()).findFirst().get();

    }


}