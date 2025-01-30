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








}







