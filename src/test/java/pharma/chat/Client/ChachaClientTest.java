package pharma.chat.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pharma.Storage.FileStorage;
import pharma.chat.Command;
import pharma.chat.Server.ChatMsg;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

class ChachaClientTest {

    @Test
    public void test() throws InterruptedException {
       BlockingQueue<ChatMsg> blockingQueue=new LinkedBlockingQueue<>();
        ObservableList<ChatMsg> observableList = FXCollections.observableArrayList();
        String jwt =
                "eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9." +
                        "eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQzMDExNTgsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLWI1MmMzNzVmLWY3YTEtNDk4ZC05MzA5LThiMTIyZDkwODUzOCIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTEwVDE2OjAwOjU4WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTEwVDE2OjAwOjU4WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTEwVDIwOjAwOjU4WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0xMFQxNjowMDo1OFoifV19LCJpYXQiOjE3NDQzMDA4NTgsImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDMwMDg1OCwic3ViIjoidXNlci1saXZlLTM5MjdkYWRiLTVkMGItNGUwZS05NzM4LWRkYTMxOGYyOTI3MCJ9.mQ4bwt7BIrPCSLsGYlJBvOjQvkzrYissNY2xNfT1E0w9CnLwvflSEH98oEIUj6ebO6WNJc3w4SNeoDRi3K5cNGK1nQOI-7uaTXUEwIp91Y0FziyrBzrT3hJhJ0jq4sPuyNn6WR1SqaOx5slzDVg50HBYEESsU0K2xQE3-55ENrEm2K-g2DwJzNIC26mn3BCNqB8hlRDW7P1jXFZGkqWl-" +
                        "RveS8W7aMgfvIn0IWjuJDoTfR5IQiLyNBs1xoBCI7bEK1Qq9pq9am46vjuw-k9bNx8VyYU4j2Viqf_So9EX8mUbtJhTRfKgkMEZxttSVuoRSgV4jZCuhL_erhvCzMqSlA";


        ChachaClient chachaClient = new ChachaClient(blockingQueue,observableList,jwt);




    }



}