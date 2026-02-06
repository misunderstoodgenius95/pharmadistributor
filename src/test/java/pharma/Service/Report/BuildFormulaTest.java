package pharma.Service.Report;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.dao.PurchaseOrderDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildFormulaTest {
    private List<UserFormula> list;
    private BuildFormula buildFormula;
    @Mock
    private PurchaseOrderDao orderDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        buildFormula = new BuildFormula(new UserFormula("formula1", "moltiplicazione(somma(iva_ordini,totale_ordini),2)"),orderDao);
    }

    @Test
    void ValidBuildQuery() {
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22,22.1);

        System.out.println(buildFormula.buildFormula());
    }

    @Test
    public void testme() {

        String value = "moltiplicazione(somma(iva_ordini,totale_ordini),2)";
        int index = value.lastIndexOf("somma");

        String substring = value.substring(index);
        int last_parentesis = substring.indexOf(")");
        System.out.println(value.substring(index, index + last_parentesis + 1));

    }

 /*   @ParameterizedTest
    @MethodSource("provideFormulaTestCases")
    @DisplayName("Test extractOperation con MethodSource")
    void testExtractOperation(String formula, String expected) {
        List<String> operations = BuildFormula.extractOperation(formula);
        Assertions.assertEquals(expected, operations.toString());
    }


    static Stream<Arguments> provideFormulaTestCases() {
        return Stream.of(
                arguments("somma(valori1,valori2)", "[somma]"),
                arguments("moltiplicazione(somma(valori1,valori2,2))", "[somma, moltiplicazione]")
        );
    }*/


    @Test
    public void me() {

        String value = "HellMeHello";
        System.out.println(value.indexOf("Hello"));

    }

    @Test
    void estraiContenutoOperazioneSomma() {
        String somma = buildFormula.estraiContenutoOperazione("somma");
        System.out.println(somma);
        Assertions.assertEquals("iva_ordini,totale_ordini", somma);
    }

    @Test
    void estraiContenutoOperazioneMoltiplicazione() {
        String moltiplicazione = buildFormula.estraiContenutoOperazione("moltiplicazione");
        System.out.println(moltiplicazione);
        //Assertions.assertEquals("iva_ordini,totale_ordini",somma);
    }

    @Test
    void extractIntoParentesis() {
     /*   List<String> attribute=buildFormula.extractAttribute();
        SoftAssertions.assertSoftly(value->{
         value.assertThat(attribute.getFirst()).isEqualTo("iva_ordini");
         value.assertThat(attribute.get(1)).isEqualTo("totale_ordini");
        });*/
    }

    @DisplayName("Valid Value")
    @ParameterizedTest
    @CsvSource({"somma(),", "somma()()"})
    void ValidcheckParentesi(String value) {
        Assertions.assertTrue(BuildFormula.checkParentesi(value));
    }

    @DisplayName("InValid Value")
    @ParameterizedTest
    @CsvSource({"somma(", "somma()("})
    void InValidcheckParentesi(String value) {
        Assertions.assertFalse(BuildFormula.checkParentesi(value));
    }

    @Test
    void ValidoperandCheck() {
        Assertions.assertTrue(BuildFormula.checkOperazioni(List.of("somma", "moltiplicazione")));
    }

    @Test
    void InValidoperandCheck() {
        Assertions.assertFalse(BuildFormula.checkOperazioni(List.of("soma", "moltipliazione")));
    }

    @Test
    void ValidcheckAttributeandOperator() {
        Assertions.assertTrue(BuildFormula.checkAttributeandOperator(List.of("iva_ordini", "totale_ordini","somma","2")));
    }

    @Test
    void InvalidcheckAttributeandOperator() {
        Assertions.assertFalse(BuildFormula.checkAttributeandOperator(List.of("iva_ordini", "totale_valori","aa","sss")));
    }

    @Test
    void extractOperation() {
        List<String> actual = BuildFormula.extractOperation("moltiplicazione(somma(valore1,valore2))");
        System.out.println(actual.toString());
    }


    @Test
    void ValidtestExtractIntoParentesisSingle() {


        List<String> valore=BuildFormula.extractIntoParentesis("somma(valore1,valore2)");
     valore.forEach(System.out::println);
    }
    @Test
    void ValidtestExtractIntoParentesisMultiple() {
        List<String> valore=BuildFormula.extractIntoParentesis("moltiplicazione(somma(valore1,valore2),2)");
        valore.forEach(System.out::println);
    }

    @Test
    void extractSubstring(){
        List<String> list1=BuildFormula.extractIntoParentesis("moltiplicazione(somma(totale_ordini,iva_ordini),2)");
        System.out.println(list1);


    }

    @Test
    void testExtractIntoParentesis() {
        List<String> list1=BuildFormula.extractIntoParentesis("moltiplicazione(somma(totale_ordini,iva_ordini),2)","somma");
        System.out.println(list1);

    }
    @Test
    void testExtractIntoParentesisMolt() {
        List<String> list1=BuildFormula.extractIntoParentesis("moltiplicazione(somma(totale_ordini,iva_ordini),2)","moltiplicazione");
        list1.forEach(System.out::println);

    }

    @Test
    void checkAttribute() {
        boolean actual=BuildFormula.checkAttribute(List.of("iva_ordini","iva_valori"));

        Assertions.assertTrue(actual);


    }

/*    @Test
    void buildQuery() {
        BuildFormula.buildQuery(List.of("moltiplicazione","somma"),"moltiplicazione(somma(22,11),2)");


    }*/


    @Test
    void removeOperator() {
        List<String> listActual=BuildFormula.removeOperator(List.of("moltiplicazione(somma(22,11)","2","iva_ordini"));
        Assertions.assertEquals(2,listActual.size());
    }

    @Test
    void ValidisNumeric() {
        boolean value=BuildFormula.isNumeric("2");
        Assertions.assertTrue(value);
    }
    @Test
    void InvalidisNumeric() {
        boolean value=BuildFormula.isNumeric("totale_ordini");
        Assertions.assertFalse(value);
    }





}

