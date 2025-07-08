package pharma.Model;

import org.junit.jupiter.api.*;
import pharma.javafxlib.Dialog.CustomDialog;

class FieldDataTest  {

   @BeforeEach
    public void setup() {

    }
@Test

    public void testFieldData() {
   FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setAnagrafica_cliente("Agrafia Cliente").setSigla("AF").
            setPartita_iva("IT122222222").build();
}
    @Test

    public void testCode() {
        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setcode("11as")
                .build();
        Assertions.assertEquals("11as",fieldData.getCode());
    }


@Test
    public void get_button(){
   CustomDialog<String> customDialog=new CustomDialog<>("Aggiungi Casa Farmaceutica");
  customDialog.add_text_field("Inserisci Anagrafia Cliente");
   customDialog.add_text_field("Inserisci  Partita Iva");
    customDialog.add_text_field("Inserisci  Sigla");

int size=customDialog.getDialogPane().getButtonTypes().size();
Assertions.assertTrue(size==2);
}

    @Test
    void getNome() {

       FieldData fieldData =FieldData.FieldDataBuilder.getbuilder().setNome("Ciao").build();
       Assertions.assertEquals("Ciao",fieldData.getNome());
    }

    @Test
    void getCategoria() {
      /* FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setCategoria(1).build();
       Assertions.assertEquals(1,fieldData.getCategoria());

       */



    }
    @Test
    void get_id(){
        FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setNome("Scegli Valore").setId(1).build();
        Assertions.assertEquals(1,fieldData.getId());
    }

    @Test
    void getTipologia() {
    }

    @Test
    void getMisure() {
    }

    @Test
    void getCredit_note_number() {
       FieldData fieldData=FieldData.FieldDataBuilder.getbuilder().setCredit_note_number("100").build();
       Assertions.assertEquals("100",fieldData.getCredit_note_number());
    }
}