package pharma.config;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TableUtilityTest {

    @Test
    public  void test(){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());

        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy H mm");

        // Format and print
        String formattedDate = localDateTime.format(formatter);
        System.out.println(formattedDate);

    }

}