package pharma.Controller.subpanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pharma.Stages;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class ChatTest {

    @Start
    public void start(Stage primaryStage) throws IOException {
        Stages stage = new Stages();
        FXMLLoader loader=stage.load("/subpanel/chat.fxml");

        Scene scene = new Scene(loader.load());

        //primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    @Test
    public void test(FxRobot robot){

        robot.sleep(900000);

    }

    @Test
    void extracted_message() {
        String jsonString_wait = "{\"type\":\"Join\",\"ws_seller\":{\"_events\":{\"close\":[null,null]},\"_eventsCount\":2,\"_binaryType\":\"nodebuffer\",\"_closeCode\":1006,\"_closeFrameReceived\":false,\"_closeFrameSent\":false,\"_closeMessage\":{\"type\":\"Buffer\",\"data\":[]},\"_closeTimer\":null,\"_errorEmitted\":false,\"_extensions\":{},\"_paused\":false,\"_protocol\":\"\",\"_readyState\":1,\"_receiver\":{\"_events\":{},\"_writableState\":{\"highWaterMark\":65536,\"length\":0,\"corked\":0,\"writelen\":0,\"bufferedIndex\":0,\"pendingcb\":0},\"_allowSynchronousEvents\":true,\"_binaryType\":\"nodebuffer\",\"_extensions\":{},\"_isServer\":true,\"_maxPayload\":104857600,\"_skipUTF8Validation\":false,\"_bufferedBytes\":0,\"_buffers\":[],\"_compressed\":false,\"_payloadLength\":79,\"_mask\":{\"type\":\"Buffer\",\"data\":[141,124,7,185]},\"_fragmented\":0,\"_masked\":true,\"_fin\":true,\"_opcode\":1,\"_totalPayloadLength\":0,\"_messageLength\":0,\"_fragments\":[],\"_errored\":false,\"_loop\":false,\"_state\":0,\"_eventsCount\":6},\"_sender\":{\"_extensions\":{},\"_socket\":{\"connecting\":false,\"_hadError\":false,\"_parent\":null,\"_host\":null,\"_closeAfterHandlingError\":false,\"_events\":{\"end\":[null,null]},\"_readableState\":{\"highWaterMark\":65536,\"buffer\":[],\"bufferIndex\":0,\"length\":0,\"pipes\":[],\"awaitDrainWriters\":null},\"_writableState\":{\"highWaterMark\":65536,\"length\":0,\"corked\":0,\"writelen\":0,\"bufferedIndex\":0,\"pendingcb\":0},\"allowHalfOpen\":true,\"_eventsCount\":4,\"_sockname\":null,\"_pendingData\":null,\"_pendingEncoding\":\"\",\"server\":{\"requestTimeout\":300000,\"headersTimeout\":60000,\"keepAliveTimeout\":5000,\"connectionsCheckingInterval\":30000,\"requireHostHeader\":true,\"rejectNonStandardBodyWrites\":false,\"_events\":{\"listening\":[null,null]},\"_eventsCount\":5,\"_connections\":2,\"_handle\":{\"reading\":false},\"_usingWorkers\":false,\"_workers\":[],\"_unref\":false,\"_listeningId\":2,\"allowHalfOpen\":true,\"pauseOnConnect\":false,\"noDelay\":true,\"keepAlive\":false,\"keepAliveInitialDelay\":0,\"highWaterMark\":65536,\"httpAllowHalfOpen\":false,\"timeout\":0,\"maxHeadersCount\":null,\"maxRequestsPerSocket\":0,\"_connectionKey\":\"6::::8081\"},\"_server\":{\"requestTimeout\":300000,\"headersTimeout\":60000,\"keepAliveTimeout\":5000,\"connectionsCheckingInterval\":30000,\"requireHostHeader\":true,\"rejectNonStandardBodyWrites\":false,\"_events\":{\"listening\":[null,null]},\"_eventsCount\":5,\"_connections\":2,\"_handle\":{\"reading\":false},\"_usingWorkers\":false,\"_workers\":[],\"_unref\":false,\"_listeningId\":2,\"allowHalfOpen\":true,\"pauseOnConnect\":false,\"noDelay\":true,\"keepAlive\":false,\"keepAliveInitialDelay\":0,\"highWaterMark\":65536,\"httpAllowHalfOpen\":false,\"timeout\":0,\"maxHeadersCount\":null,\"maxRequestsPerSocket\":0,\"_connectionKey\":\"6::::8081\"},\"parser\":null,\"_paused\":false,\"timeout\":0},\"_firstFragment\":true,\"_compress\":false,\"_bufferedBytes\":0,\"_queue\":[],\"_state\":0},\"_socket\":{\"connecting\":false,\"_hadError\":false,\"_parent\":null,\"_host\":null,\"_closeAfterHandlingError\":false,\"_events\":{\"end\":[null,null]},\"_readableState\":{\"highWaterMark\":65536,\"buffer\":[],\"bufferIndex\":0,\"length\":0,\"pipes\":[],\"awaitDrainWriters\":null},\"_writableState\":{\"highWaterMark\":65536,\"length\":0,\"corked\":0,\"writelen\":0,\"bufferedIndex\":0,\"pendingcb\":0},\"allowHalfOpen\":true,\"_eventsCount\":4,\"_sockname\":null,\"_pendingData\":null,\"_pendingEncoding\":\"\",\"server\":{\"requestTimeout\":300000,\"headersTimeout\":60000,\"keepAliveTimeout\":5000,\"connectionsCheckingInterval\":30000,\"requireHostHeader\":true,\"rejectNonStandardBodyWrites\":false,\"_events\":{\"listening\":[null,null]},\"_eventsCount\":5,\"_connections\":2,\"_handle\":{\"reading\":false},\"_usingWorkers\":false,\"_workers\":[],\"_unref\":false,\"_listeningId\":2,\"allowHalfOpen\":true,\"pauseOnConnect\":false,\"noDelay\":true,\"keepAlive\":false,\"keepAliveInitialDelay\":0,\"highWaterMark\":65536,\"httpAllowHalfOpen\":false,\"timeout\":0,\"maxHeadersCount\":null,\"maxRequestsPerSocket\":0,\"_connectionKey\":\"6::::8081\"},\"_server\":{\"requestTimeout\":300000,\"headersTimeout\":60000,\"keepAliveTimeout\":5000,\"connectionsCheckingInterval\":30000,\"requireHostHeader\":true,\"rejectNonStandardBodyWrites\":false,\"_events\":{\"listening\":[null,null]},\"_eventsCount\":5,\"_connections\":2,\"_handle\":{\"reading\":false},\"_usingWorkers\":false,\"_workers\":[],\"_unref\":false,\"_listeningId\":2,\"allowHalfOpen\":true,\"pauseOnConnect\":false,\"noDelay\":true,\"keepAlive\":false,\"keepAliveInitialDelay\":0,\"highWaterMark\":65536,\"httpAllowHalfOpen\":false,\"timeout\":0,\"maxHeadersCount\":null,\"maxRequestsPerSocket\":0,\"_connectionKey\":\"6::::8081\"},\"parser\":null,\"_paused\":false,\"timeout\":0},\"_autoPong\":true,\"_isServer\":true},\"Pharmacist\":\"pharmacist@example.com\",\"message\":\"Join Chat\"}";


    }
}