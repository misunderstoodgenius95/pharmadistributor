
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pharma.Model.FieldData;
import pharma.Storage.StorageToken;
import pharma.security.TokenUtility;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class JwtExtractTest {

    private static String token;

    @BeforeAll
    static void setUpAll() {
        //token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFuVmtvcFMyVWREVm01LXVEWkZwSCJ9.eyJyb2xlIjoicHVyY2hhc2UiLCJpc3MiOiJodHRwczovL2Rldi1tZDAwM3N5ZThsYnM4azdnLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NzQ5ZmJkM2I3Y2IzYTJhOTJkNDA2NjEiLCJhdWQiOiJodHRwczovL2Rpc3Ryb2FwaS5jb20iLCJpYXQiOjE3MzM0ODI3NTksImV4cCI6MTczMzU2OTE1OSwiZ3R5IjoicGFzc3dvcmQiLCJhenAiOiJwMTRwWnpib0tKZUNjSWpmbWtiM255VEpvRDE0bWYxciIsInBlcm1pc3Npb25zIjpbIndyaXRlOmFkZF9waGFyYW1hIl19.A67uusCDNj2Jq0H0XgBclvuDitCqT3RXZO9TwYtrPDWWRkp6tyEowaTLnjREK3vvOBdtgY40QdOSJAh5aeowyQs1s2q6vlDEZt-Cg3CYsSw6dXOsJfwp9Zg4oSy303wEPh2h2pZ1HLZQVXa5dEZ4ZLojXhSL9uCy2LS0TiX2EHyJNIXspynZcN7TMmaE4fJ_VtuDpxQUF_-aVDXHVQn-41JM1kpEqHHQvdHhIo3wQ-ZFTbIMfgxDjnz-OSeoOSsRGJ2R2c334y7yIJHUcm_pImikmRbWBUw6HOnPRyzzn-MSw-Z86Pr-m_vciF4tuzWwR66Q-WAH0eJLVpUGbPoaxQ";
        token=StorageToken.get_token();
    }

    @Test

    public void check_roles() {
        String purchase = "purchase";
        Assertions.assertEquals("purchase", TokenUtility.extractRole(token).replace("\"", ""));
    }


/*
    @Test
    void check_permissions() {

 boolean actual= TokenUtility.check_permission(token,"write","pharma");
Assertions.assertTrue(actual);
    }
*/


@Test
    public void extract_permission(){
        String  token="{\"request_id\":\"request-id-live-c11cb435-fec0-4980-adfb-3e1428125dc3\",\"session\":{\"attributes\":{\"ip_address\":\"\",\"user_agent\":\"\"},\"authentication_factors\":[{\"created_at\":\"2025-04-09T13:47:28Z\",\"delivery_method\":\"knowledge\",\"last_authenticated_at\":\"2025-04-09T13:47:27Z\",\"type\":\"password\",\"updated_at\":\"2025-04-09T13:47:28Z\"}],\"custom_claims\":{},\"expires_at\":\"2025-04-09T17:47:27Z\",\"last_accessed_at\":\"2025-04-09T15:29:08Z\",\"session_id\":\"session-live-079375ec-5f7f-4e83-85e4-2667f47129f7\",\"started_at\":\"2025-04-09T13:47:27Z\",\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\"},\"session_jwt\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6Imp3ay1saXZlLWJkOTNiMWYzLWQ3ZjAtNGY1NS1iMDY1LTgyYzA0MWFkZDVkNSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiJdLCJleHAiOjE3NDQyMTI4NDgsImh0dHBzOi8vc3R5dGNoLmNvbS9zZXNzaW9uIjp7ImlkIjoic2Vzc2lvbi1saXZlLTA3OTM3NWVjLTVmN2YtNGU4My04NWU0LTI2NjdmNDcxMjlmNyIsInN0YXJ0ZWRfYXQiOiIyMDI1LTA0LTA5VDEzOjQ3OjI3WiIsImxhc3RfYWNjZXNzZWRfYXQiOiIyMDI1LTA0LTA5VDE1OjI5OjA4WiIsImV4cGlyZXNfYXQiOiIyMDI1LTA0LTA5VDE3OjQ3OjI3WiIsImF0dHJpYnV0ZXMiOnsidXNlcl9hZ2VudCI6IiIsImlwX2FkZHJlc3MiOiIifSwiYXV0aGVudGljYXRpb25fZmFjdG9ycyI6W3sidHlwZSI6InBhc3N3b3JkIiwiZGVsaXZlcnlfbWV0aG9kIjoia25vd2xlZGdlIiwibGFzdF9hdXRoZW50aWNhdGVkX2F0IjoiMjAyNS0wNC0wOVQxMzo0NzoyN1oifV19LCJpYXQiOjE3NDQyMTI1NDgsImlzcyI6InN0eXRjaC5jb20vcHJvamVjdC1saXZlLThlMmZmNTExLTNhZTgtNGRiYy1hYWVhLWQyMzQ1MzE3NDI2MiIsIm5iZiI6MTc0NDIxMjU0OCwic3ViIjoidXNlci1saXZlLTVhYzNlOWJkLWVhMDMtNDJhMC1hZDBiLWRmZTI0ZWUxMTI4NiJ9.Nu-1kkxrkwP60RF06wszcKA8pQFARiIhOMKhK46EZDe8f2m-iNQIv6ysuo4vDQA1dkuBd1CGjvbCTX7WP4XjW8iJLqSHGfjowlCOo0sez0zmq2JRBx3h0pvpo3fqGCZjD1Eh1CSgQcd6pUFrKbRd18JUyQthCIkOHnnBYenPg93LB9ePjPKm9o-dlUET_DxhNtd6pOBnABNiHnuworzhFfKIYN-UXw-STX1cjgpHtGBAcry9i_vmNv5E7RT7T4CZYU06fWc54IwhwHvvUrYn1IQCp0bVQDDHGaJ4QpM8ny9o3_7Z8UGeuxhsLlnesYY1bmPwGIFMLa3SXIW9cuvacw\",\"session_token\":\"\",\"status_code\":200,\"user\":{\"biometric_registrations\":[],\"created_at\":\"2025-04-08T11:35:48Z\",\"crypto_wallets\":[],\"emails\":[{\"email\":\"luigi.bianchi@azienda.com\",\"email_id\":\"email-live-5cf4bf17-1059-4962-a7ac-9cdb3d7c98a9\",\"verified\":false}],\"external_id\":null,\"name\":{\"first_name\":\"luigi\",\"last_name\":\"bianchi\",\"middle_name\":\"\"},\"password\":{\"password_id\":\"password-live-67052b05-bcd4-46c8-9fe1-510b54ee5ade\",\"requires_reset\":false},\"phone_numbers\":[],\"providers\":[],\"status\":\"active\",\"totps\":[],\"trusted_metadata\":{\"permissions\":{\"pharma\":[\"create\",\"view\",\"update\"]},\"role\":\"purchase\"},\"untrusted_metadata\":{},\"user_id\":\"user-live-5ac3e9bd-ea03-42a0-ad0b-dfe24ee11286\",\"webauthn_registrations\":[]}}\n";
       String permissions= TokenUtility.extractPermissions(token);
    System.out.println(permissions);
    Assertions.assertTrue(TokenUtility.check_permission("pharma","view",permissions));



    }
    @Test
    public  void  test(){


        FieldData data1 = FieldData.FieldDataBuilder.getbuilder().setcode("L001")
                .setNome("Paracetamolo")
                .setNome_categoria("Antidolorifico")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Paracetamolo")
                .setNome_casa_farmaceutica("Pharma S.p.A").
                setQuantity(50).setProduction_date(Date.valueOf(LocalDate.of(2023, 01, 15))).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 01, 15))).
                setAvailability(300)
                .build();
        FieldData data2 = FieldData.FieldDataBuilder.getbuilder()
                .setcode("L002")
                .setNome("Ibuprofene")
                .setNome_categoria("Antinfiammatorio")
                .setNome_tipologia("Compresse")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Ibuprofene")
                .setNome_casa_farmaceutica("Salute SRL")
                .setQuantity(30)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 5, 10)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2025, 5, 10)))
                .setAvailability(150)
                .build();

        FieldData data3 = FieldData.FieldDataBuilder.getbuilder()
                .setcode("L003")
                .setNome("Amoxicillina")
                .setNome_categoria("Antibiotico")
                .setNome_tipologia("Capsule")
                .setUnit_misure("mg")
                .setNome_principio_attivo("Amoxicillina")
                .setNome_casa_farmaceutica("Medicina Generale")
                .setQuantity(20)
                .setProduction_date(Date.valueOf(LocalDate.of(2023, 3, 20)))
                .setElapsed_date(Date.valueOf(LocalDate.of(2024, 3, 20)))
                .setAvailability(0)
                .build();
        ArrayList<FieldData> arrayList=new ArrayList<>();
        arrayList.add(data1);
        arrayList.add(data2);
        arrayList.add(data3);
        ObservableList<FieldData> observableList= FXCollections.observableArrayList(arrayList);

    }




}