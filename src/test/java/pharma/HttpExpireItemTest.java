package pharma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import net.jodah.failsafe.internal.util.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.config.net.ClientHttp;
import pharma.javafxlib.test.SimulateEvents;

import java.io.File;
import java.lang.reflect.Field;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

 public class HttpExpireItemTest {
    @Mock
    private HttpResponse<String> httpResponse;
    @Mock
    private  ClientHttp clientHttp;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    public  static  ObservableList<FieldData> get_list(){
        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("11a").
                setFarmaco_id(30).
                setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
        FieldData fieldData_1 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("31a").
                setFarmaco_id(20).
                setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
        FieldData fieldData_3 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("21a").
                setFarmaco_id(10).
                setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
        return  FXCollections.observableArrayList(fieldData,fieldData_1,fieldData_3);


    }
    public static JSONArray get_json_array(){
        JSONObject json1 = new JSONObject();
        json1.put("lotto_id", "11a");
        json1.put("product_id", 60);
        json1.put("expiration_date", "10/10/2028");
        json1.put("time_of_day", 30);

        JSONObject json2 = new JSONObject();
        json2.put("lotto_id", "31a");
        json2.put("product_id", 20);
        json2.put("expiration_date", "10/10/2028");
        json2.put("time_of_day", 30);

        JSONObject json3 = new JSONObject();
        json3.put("lotto_id", "41a");
        json3.put("product_id", 20);
        json3.put("expiration_date", "10/10/2028");
        json3.put("time_of_day", 30);
        return new   JSONArray().put(json3).put(json2).put(json1);

    }

    @Nested
    class PostExpire {
        @Test
        void InValidListNullpost_expire() {


            HttpExpireItem httpExpireItem = new HttpExpireItem(clientHttp);
            when(httpResponse.statusCode()).thenReturn(201);
            when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);


            try (MockedStatic<HttpExpireItem> mockedStatic = Mockito.mockStatic(HttpExpireItem.class)) {
                mockedStatic.when(() -> HttpExpireItem.fieldata_to_json_array(eq(get_list()), Mockito.anyInt())).thenReturn(get_json_array());


            } catch (Exception e) {
                e.printStackTrace();
            }


           Assertions.assertThrows(NullPointerException.class,()->{
               httpExpireItem.post_expire("http://localhost:3000/expire_item", null, 30);
           });


        }
        @Test
        void InValidListEmptypost_expire() {


            HttpExpireItem httpExpireItem = new HttpExpireItem(clientHttp);
            when(httpResponse.statusCode()).thenReturn(201);
            when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);


            try (MockedStatic<HttpExpireItem> mockedStatic = Mockito.mockStatic(HttpExpireItem.class)) {
                mockedStatic.when(() -> HttpExpireItem.fieldata_to_json_array(eq(get_list()), Mockito.anyInt())).thenReturn(get_json_array());


            } catch (Exception e) {
                e.printStackTrace();
            }


            Assertions.assertThrows(IllegalArgumentException.class,()->{
                httpExpireItem.post_expire("http://localhost:3000/expire_item", FXCollections.observableArrayList(), 30);
            });


        }
        @Test
        void InValidExpireDaypost_expire() {


            HttpExpireItem httpExpireItem = new HttpExpireItem(clientHttp);
            when(httpResponse.statusCode()).thenReturn(200);
            when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);


            try (MockedStatic<HttpExpireItem> mockedStatic = Mockito.mockStatic(HttpExpireItem.class)) {
                mockedStatic.when(() -> HttpExpireItem.fieldata_to_json_array(eq(get_list()), Mockito.anyInt())).thenReturn(get_json_array());


            } catch (Exception e) {
                e.printStackTrace();
            }


            Assertions.assertThrows(IllegalArgumentException.class,()->{
                httpExpireItem.post_expire("http://localhost:3000/expire_item", FXCollections.observableArrayList(), 0);
            });


        }
        @Test
        void Validpost_expire() {


            HttpExpireItem httpExpireItem = new HttpExpireItem(clientHttp);
            when(httpResponse.statusCode()).thenReturn(200);
            when(clientHttp.send(Mockito.any(HttpRequest.class))).thenReturn(httpResponse);


            try (MockedStatic<HttpExpireItem> mockedStatic = Mockito.mockStatic(HttpExpireItem.class)) {
                mockedStatic.when(() -> HttpExpireItem.fieldata_to_json_array(eq(get_list()), Mockito.anyInt())).thenReturn(get_json_array());


            } catch (Exception e) {
                e.printStackTrace();
            }


            int status_actual = httpExpireItem.post_expire("http://localhost:3000/expire_item", get_list(), 30);
            Assertions.assertEquals(200, status_actual);

        }
    }

    @Nested
    class FileDatatoJson {

        @Test
        void Validfieldata_to_json() {
            JSONObject jsonObject_actutal = new JSONObject();
            jsonObject_actutal.put("lotto_id", "11a");
            jsonObject_actutal.put("farmaco_id", 60);
            jsonObject_actutal.put("expiration_date", "10/10/2028");
            jsonObject_actutal.put("time_of_day", 30);
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("11a").setFarmaco_id(60).setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            JSONObject expected = HttpExpireItem.fieldata_to_json(fieldData, 30);
            System.out.println(expected);
            Assertions.assertEquals(expected.toString(), jsonObject_actutal.toString());

        }

        @Nested
        class InvalidInputValuesTestthrownExeption {


            @Test
            void InvalidFilDatafieldata_to_json() {
                JSONObject jsonObject_actutal = new JSONObject();
                jsonObject_actutal.put("lotto_id", "11a");
                jsonObject_actutal.put("farmaco_id", 60);
                jsonObject_actutal.put("expiration_date", "10/10/2028");
                jsonObject_actutal.put("time_of_day", 30);

                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    HttpExpireItem.fieldata_to_json(null, 30);


                });


            }

            @Test
            void InvalidExpirationlessZerolDatafieldata_to_json() {
                JSONObject jsonObject_actutal = new JSONObject();
                jsonObject_actutal.put("lotto_id", "11a");
                jsonObject_actutal.put("farmaco_id", 60);
                jsonObject_actutal.put("expiration_date", "10/10/2028");
                jsonObject_actutal.put("time_of_day", 30);
                FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("11a").setFarmaco_id(60).setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();

                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    HttpExpireItem.fieldata_to_json(fieldData, 0);


                });
            }

            @Test
            void INValiExpireDateNulldfieldata_to_json() {
                JSONObject jsonObject_actutal = new JSONObject();
                jsonObject_actutal.put("lotto_id", "11a");
                jsonObject_actutal.put("farmaco_id", 60);
                jsonObject_actutal.put("expiration_date", "10/10/2028");
                jsonObject_actutal.put("time_of_day", 30);
                FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(60).build();

                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    HttpExpireItem.fieldata_to_json(fieldData, 30);


                });


            }


        }

        @Test
        void InvalidFiled_to_json() {
            JSONObject jsonObject_actutal = new JSONObject();
            jsonObject_actutal.put("lotto_id", "11a");
            jsonObject_actutal.put("farmaco_id", 60);
            jsonObject_actutal.put("expiration_date", "10/10/2028");
            jsonObject_actutal.put("time_of_day", 30);
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setFarmaco_id(60).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            JSONObject expected = HttpExpireItem.fieldata_to_json(fieldData, 30);
            System.out.println(expected);
            Assertions.assertNotEquals(expected.toString(), jsonObject_actutal.toString());

        }
    }

    @Nested
    class Fieldata_to_json_array{

        @Test
        public void InvalidObsNullFieldata_to_json_array(){
            Assertions.assertThrows(IllegalArgumentException.class,()-> {

         HttpExpireItem.fieldata_to_json_array(null, 30);
            });


        }
        @Test
        public void InvalidObsEmptyFieldata_to_json_array(){
            Assertions.assertThrows(IllegalArgumentException.class,()-> {

                HttpExpireItem.fieldata_to_json_array(FXCollections.observableArrayList(), 30);
            });


        }
        @Test
        public void Invalid_Zero_Expiration_days_Fieldata_to_json_array(){
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("11a").
                    setFarmaco_id(30).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            FieldData fieldData_1 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("31a").
                    setFarmaco_id(20).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            FieldData fieldData_3 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("21a").
                    setFarmaco_id(10).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            Assertions.assertThrows(IllegalArgumentException.class,()-> {

                HttpExpireItem.fieldata_to_json_array(FXCollections.observableArrayList(fieldData,fieldData_1,fieldData_3), 0);

            });


        }
        @Test
        public void ValidFieldata_to_json_array(){
            FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("11a").
                    setFarmaco_id(30).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();

            JSONArray jsonArray_expected = new JSONArray();

            JSONObject json1 = new JSONObject();
            json1.put("lotto_id", "11a");
            json1.put("product_id", 30);
            json1.put("expiration_date", "10/10/2028");
            json1.put("time_of_day", 30);

            FieldData fieldData_2 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("31a").
                    setFarmaco_id(20).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();
            JSONObject json2 = new JSONObject();
            json2.put("lotto_id", "31a");
            json2.put("farmaco_id", 20);
            json2.put("expiration_date", "10/10/2028");
            json2.put("time_of_day", 30);

            JSONObject json3 = new JSONObject();
            json3.put("lotto_id", "41a");
            json3.put("farmaco_id", 20);
            json3.put("expiration_date", "10/10/2028");
            json3.put("time_of_day", 30);
            jsonArray_expected.putAll(List.of(json1,json2,json3));

            FieldData fieldData_3 = FieldData.FieldDataBuilder.getbuilder().setLotto_id("41a").
                    setFarmaco_id(20).
                    setElapsed_date(Date.valueOf(LocalDate.of(2028, 10, 10))).build();

try(MockedStatic<HttpExpireItem> mockedStatic= Mockito.mockStatic(HttpExpireItem.class)){
    mockedStatic.when(()->HttpExpireItem.fieldata_to_json(fieldData,30)).
            thenReturn(json1);
    mockedStatic.when(()->HttpExpireItem.fieldata_to_json(fieldData_2,30)).
            thenReturn(json2);
    mockedStatic.when(()->HttpExpireItem.fieldata_to_json(fieldData_3,30)).
            thenReturn(json3);


}


        JSONArray jsonArray_actual=HttpExpireItem.fieldata_to_json_array(FXCollections.observableArrayList(fieldData,fieldData_2,fieldData_3), 30);

      Assertions.assertEquals(jsonArray_expected.toString(),jsonArray_actual.toString());




        }









    }

    @AfterEach
    public void TearDown(){
        clientHttp=null;
        httpResponse=null;


    }

    @Test
    public  void ValidIntegrationTest(){
        HttpExpireItem httpExpireItem=new HttpExpireItem(new ClientHttp());
        int status=httpExpireItem.post_expire("http://localhost:3000/expire_items",get_list(),30);
        System.out.println();
        Assertions.assertEquals(201,status);

    }




}