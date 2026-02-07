package pharma.Service.Report;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.config.InvalidFormulaException;
import pharma.dao.PurchaseOrderDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BuildFormulaTest {


    @Mock
    private PurchaseOrderDao orderDao;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


    }

    @Test
    void ValidBuildQueryWithMoltiplicazioneandSum() throws InvalidFormulaException {
         BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "moltiplicazione(somma(iva_ordini,totale_ordini),2)"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22,22.1);

        System.out.println(buildFormula.buildFormula());
    }
    @Test
    void ValidBuildQueryWithMax() throws InvalidFormulaException {
        BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "somma(max(totale_ordini),iva_ordini)"),orderDao);
       when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        System.out.println(buildFormula.buildFormula());
    }
    @Test
    void ValidBuildQueryWithDeviazioneStandard() throws InvalidFormulaException {
        BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "deviazione(totale_ordini)"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        System.out.println(buildFormula.buildFormula());
    }
    @Test
    void InvalidBuildQUeryWithParentesisNotOpen() throws InvalidFormulaException {
         BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "somma(max(totale_ordini),iva_ordini"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        Assertions.assertThrows(InvalidFormulaException.class, buildFormula::buildFormula);

    }
    @Test
    void InvalidBuildQUeryWithAttributeNotPresent() throws InvalidFormulaException {
         BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "somma(max(11),22)"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        Assertions.assertThrows(InvalidFormulaException.class, buildFormula::buildFormula);
    }
    @Test
    void InvalidBuildQUeryWithAttributeWrong() throws InvalidFormulaException {
       BuildFormula  buildFormula = new BuildFormula(new UserFormula("formula1", "somma(max(totale_ordine),22)"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        Assertions.assertThrows(InvalidFormulaException.class, buildFormula::buildFormula);
    }
    @Test
    void InvalidBuildQUeryWithOperatoreWrong() throws InvalidFormulaException {
      BuildFormula  buildFormula = new BuildFormula(new UserFormula("formula1", "soma(max(totale_ordini),22)"),orderDao);
        when(orderDao.findBySumAggregate(Mockito.anyString())).thenReturn(10.22);
        when(orderDao.findByValue(Mockito.anyString())).thenReturn(List.of(19.11,22.11));
        InvalidFormulaException exception = Assertions.assertThrows(
                InvalidFormulaException.class,
                buildFormula::buildFormula
        );

        Assertions.assertEquals("Operatori non corretti", exception.getMessage());
    }







    @Test
    void estraiContenutoOperazioneSomma() {
      BuildFormula  buildFormula = new BuildFormula(new UserFormula("formula1", "somma(moltiplicazione(totale_ordini),iva_ordini)"),orderDao);
        String somma = buildFormula.estraiContenutoOperazione("somma");

        Assertions.assertEquals("moltiplicazione(totale_ordini),iva_ordini", somma);
    }

    @Test
    void estraiContenutoOperazioneMoltiplicazione() {
       BuildFormula buildFormula = new BuildFormula(new UserFormula("formula1", "somma(moltiplicazione(iva_ordini,totale_ordini),iva_ordini)"),orderDao);
        String actual = buildFormula.estraiContenutoOperazione("moltiplicazione");
        Assertions.assertEquals("iva_ordini,totale_ordini",actual);
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

