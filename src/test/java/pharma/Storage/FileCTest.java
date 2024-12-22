package pharma.Storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileC.class)
class FileCTest {

    @Test
    void getProperties() throws IOException {
        List<String> list=new ArrayList<>(Arrays.asList("key1","key2"));
         Properties props= mock(Properties.class);
        String propertiesContent = "key1=value1\nkey2=value2";
        StringReader stringReader = new StringReader(propertiesContent);
        FileReader fileReader= mock(FileReader.class);
        // Stub the `read` method of the mock to delegate to the StringReader
        when(fileReader.read(any(char[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return stringReader.read((char[]) args[0], (int) args[1], (int) args[2]);
        });

;


//Act
   HashMap<String,String> hashMap=FileC.getProperties(list,fileReader);
   Assertions.assertEquals("key1",hashMap.get("key1"));


    }

    @Test

    public void testGetProperties() throws Exception {
        // Mock the FileReader and Properties objects
        FileReader mockFileReader = mock(FileReader.class);
        /*Properties mockProperties = mock(Properties.class);

        // Mock the behavior of the Properties object
        when(mockProperties.getProperty("key1")).thenReturn("value1");
        when(mockProperties.getProperty("key2")).thenReturn("value2");

        // Mock the static method in FileC
        PowerMockito.mockStatic(Class.forName("pharma.Storage.FileC")); // Dynamically load the class
        PowerMockito.when(Class.forName("pharma.Storage.FileC"), "load_properties", mockFileReader)
                .thenReturn(mockProperties);
*/
        // Given
        List<String> keys = Arrays.asList("key1", "key2");
        HashMap<String, String> expected = new HashMap<>();
        expected.put("key1", "value1");
        expected.put("key2", "value2");

        // When

        // Then
        assertEquals(expected, result);
    }

}