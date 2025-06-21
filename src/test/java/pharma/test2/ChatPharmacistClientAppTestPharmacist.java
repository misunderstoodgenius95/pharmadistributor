package pharma.test2;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ChatPharmacistClientAppTestPharmacist {

    @Start
    public void start(Stage stage){
        stage.initStyle(StageStyle.DECORATED);
        String jwt_pharmacist="eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NTAxNzk4MDksImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTRhMmU1OGZmLTM2MzItNGI1OS1iNDA2LWI1YjUyYzU2ZjhkZSIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA2LTE3VDE2OjU4OjI5WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA2LTE3VDIwOjU4OjI5WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNi0xN1QxNjo1ODoyOVoifV19LCJpYXQiOjE3NTAxNzk1MDksImlzcyI6Imh0dHBzOi8vbG9naW4uYXBpbWUub25saW5lIiwibmJmIjoxNzUwMTc5NTA5LCJzdWIiOiJ1c2VyLWxpdmUtZDEyYzA0MTEtN2E0Ny00ZmE4LTg2ZDItZjM1MDRiYTNlNzJjIn0.SToO-a2Bk1YmkAiGp07q7mNEwEL6hvQphv3b11CQrY5CCUEGmt4IEl87FkbYDKTFBZVJL5l4fhgKPl2pFXlNJZ_2Kqvz3qyKNGTGVVQ0t7TEy9no4F6byiv6W_nrs3M04LGT_20Tu9ooSq3wu09bVvJlS_IZhfOEdGNLCXbOvTbAkn8Kjqmh91XAwHMToKg_UKFz1XoMeLFNGhXIvzcVjIUFnAEetHio65wFp7QDckjEph9otZUsW4bHlR1Jt7PuCh8k1kjF1b4TUminAqnXDI2jHjjQb2rlPIw_zgztnQvKLBmdMneHnZLHHPSnQESOPc4CsCGJxhe50ucFAx7DNA";

        ChatClientApp chatClientApp=new ChatClientApp(jwt_pharmacist,"pharmacist");
        chatClientApp.start(stage);
        stage.setResizable(true);


    }

    @Test
    public void test(FxRobot robot){
        Platform.runLater(()->{



        });
        robot.sleep(100000000);


    }

}