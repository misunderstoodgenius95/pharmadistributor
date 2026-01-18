package pharma.Storage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.config.InputValidation;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FileStorageTest {
    @Test
    void ValidTest_getPropertyandFile() throws IOException {

        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();


        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A=a");
        }

        try (FileReader fileReader = new FileReader(tempFile)) {
            String value = FileStorage.getProperty("A", fileReader);
            assertEquals("a", value, "The value should match the property in the file.");
        }
    }
    @Test
    void inValidGetpropertyandValidFile() throws IOException {

        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();



        try (FileReader fileReader = new FileReader(tempFile)) {


            assertThrows(IllegalArgumentException.class, () -> FileStorage.getProperty("B", fileReader));// key not found
            assertThrows(IllegalArgumentException.class, () -> FileStorage.getProperty("", fileReader));// key is empty
            assertThrows(IllegalArgumentException.class, () -> FileStorage.getProperty(null, fileReader)); //key is null
            assertThrows(IllegalArgumentException.class,()->FileStorage.getProperty("A", null));
        }
        }


















    @Test
    void ValidtestGetProperties_All() throws IOException {
        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();


        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A=a\nB=b\nC=c");

        }
        HashMap<String,String> hashMap_excepted=new HashMap<>();
        hashMap_excepted.put("A","a");
        hashMap_excepted.put("B","b");
        hashMap_excepted.put("C","c");
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

        try (FileReader fileReader = new FileReader(tempFile)) {
            HashMap<String, String> actual_hash = FileStorage.getProperties(list, fileReader);
            assertNotSame(hashMap_excepted,actual_hash);
        }


    }
    @Test
    void testGetProperties_Some() throws IOException {
        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();


        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A=a\nB=b\n");
        }catch (IOException e){
            e.printStackTrace();
        }
        HashMap<String,String> hashMap_excepted=new HashMap<>();
        hashMap_excepted.put("A","a");
        hashMap_excepted.put("B","b");
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

        try (FileReader fileReader = new FileReader(tempFile)) {
            HashMap<String, String> actual_hash = FileStorage.getProperties(list, fileReader);
            assertNotSame(hashMap_excepted,actual_hash);
        }catch (IOException  e){
            e.printStackTrace();
        }


    }
    @Test
    void testGetProperties_Null() throws IOException {
        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A=a\nB=b\n");

        }

        try (FileReader fileReader = new FileReader(tempFile)) {

            assertThrows(IllegalArgumentException.class,()->FileStorage.getProperties(null,fileReader));
        }
    }
    @Test
    void testGetProperties_File_Null() throws IOException {

        HashMap<String,String> hashMap_excepted=new HashMap<>();
        hashMap_excepted.put("A","a");
        hashMap_excepted.put("B","b");
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));



        assertThrows(IllegalArgumentException.class,()->FileStorage.getProperties(list,null));
        assertThrows(IllegalArgumentException.class,()->FileStorage.getProperties(null,null));


    }




    @Test
    void Valid_setProperty() {
        File tempFile = null;
        FileWriter fileWriter;
        try {
            tempFile = File.createTempFile("test", ".properties");
            fileWriter=new FileWriter(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        FileStorage.setProperty("A","a",fileWriter);

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        FileReader fileReader;
        try{
            fileReader=new FileReader(tempFile);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
       String actual=FileStorage.getProperty("A",fileReader);
        Assertions.assertEquals("a",actual);



    }

    @Test
    void Invalid_setProperty() {
        File tempFile = null;
        FileWriter fileWriter;
        try {
            tempFile = File.createTempFile("test", ".properties");
            fileWriter=new FileWriter(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Assertions.assertThrows(IllegalArgumentException.class,()->FileStorage.setProperty(""," ",fileWriter));
        Assertions.assertThrows(IllegalArgumentException.class,()->FileStorage.setProperty(null,null ,fileWriter));
        Assertions.assertThrows(IllegalArgumentException.class,()->FileStorage.setProperty("A",null ,fileWriter));
        Assertions.assertThrows(IllegalArgumentException.class,()->FileStorage.setProperty(null,"a" ,fileWriter));
        Assertions.assertThrows(IllegalArgumentException.class,()->FileStorage.setProperty(null,null ,null));
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }

    @Test void  get_real_properties() throws IOException {
        File tempFile = File.createTempFile("test", ".properties");
        tempFile.deleteOnExit();


        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A=a\nB=b\nC=c");

        }
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
    Properties properties=new Properties();
    properties.setProperty("A","a");
    properties.setProperty("B","b");
    properties.setProperty("C","c");



        try (FileReader fileReader = new FileReader(tempFile)) {
            Properties properties_actual= FileStorage.getProperties_real(list, fileReader);
           assertEquals(properties,properties_actual);
        }






    }





@Test
    public void storage() throws IOException {
        File tempFile = null;
        FileWriter fileWriter;
        try {
            tempFile = File.createTempFile("test", ".properties");
            fileWriter=new FileWriter(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("CLIENT_ID","p14pZzboKJeCcIjfmkb3nyTJoD14mf1r");
        hashMap.put("CLIENT_SECRET","p6t-4gyYZAarQo2-H3Jygpk00I0esL0UMO4V869WXGQ_4_HJXMYgP9xlUphK3wsa");
        hashMap.put("GRANT_TYPE","password");
        hashMap.put("AUDIENCE","https://distroapi.com");
        FileStorage.setProperties(hashMap,fileWriter);
        fileWriter.close();


        HashMap<String,String> hashMap_json= FileStorage.getProperties(List.of("CLIENT_ID","CLIENT_SECRET","GRANT_TYPE","AUDIENCE"),new FileReader(tempFile));
    assertEquals(hashMap, hashMap_json);


    }


    @Test
    public void ValidIntegrationToken(){
        try {

             String jwt_actual="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQyMDYwOTksImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLWI1YTdmYmUzLTM1YzgtNDA5Mi1hZjVhLTAxZjZiMDRhNzEwOSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTA5VDEzOjM2OjM5WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTA5VDEzOjM2OjM5WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTA5VDE3OjM2OjM5WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0wOVQxMzozNjozOVoifV19LCJpYXQiOjE3NDQyMDU3OTksImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDIwNTc5OSwic3ViIjoidXNlci1saXZlLTVhYzNlOWJkLWVhMDMtNDJhMC1hZDBiLWRmZTI0ZWUxMTI4NiJ9.uT9Wa2-zRuR3c3Uc-kkO5wmu1s1Z6bv6_Q3bMPvD7BtlMYBefc8w1IzYIqClZvEZk9xorlHSPZ-JzXFfX3U5kVYK7W1619XyPFsbyGbBP1V9UXPzAm49P4Nh2jvTY1sm3Rki9LoqfIUBtmJxm_aHGCfMll682U9ZnCkowvCTwp3aeiJz4JpnZ2Fk-EhbpKHfn462hwrytV_hbdDiBbXphw5254u_ZjK-AnaiK3SfOpmKgQ9eQ-gPsBf6gDkYp6GXsS8o4UUpwYisSIRjxgieVElwjApYQRmK-OeIC4acKH6eVtXK5Lchee8zDno7Tf07U8usTduKjFGNccIuqFOE4Q";
             String jwt=FileStorage.getProperty("jwt",new FileReader("jwt.properties"));
             Assertions.assertEquals(jwt_actual,jwt);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }








}







