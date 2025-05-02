package pharma.dao;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.postgresql.core.Parser;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LottiDaoTest {
    private Database database;
    private LottiDao lottiDao;

    @BeforeEach
    public void setUp() {
        database = Mockito.mock(Database.class);
        lottiDao = new LottiDao(database, "lotto");

    }

    @Test
    public void ValidInsert() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);


        FieldData fieldData = FieldData.FieldDataBuilder.getbuilder().setLotto_id("b9188j").setTipologia(1).
                setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 10))).
                setElapsed_date(Date.valueOf(LocalDate.of(2025, 10, 01))).build();
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
        Assertions.assertTrue(lottiDao.insert(fieldData));
        verify(database).execute_prepared_query("INSERT INTO lotto (id,farmaco,production_date,elapsed_date) VALUES(?,?,?,?)");
        verify(preparedStatement).setString(1, "b9188j");
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).setDate(3, Date.valueOf(LocalDate.of(2024, 10, 10)));
        verify(preparedStatement).setDate(4, Date.valueOf(LocalDate.of(2025, 10, 01)));


    }

    @Test
    public void ValidFindAll() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(database.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("id")).thenReturn("aaa");
        when(resultSet.getDate("production_date")).thenReturn(Date.valueOf(LocalDate.of(2024, 10, 01)));
        when(resultSet.getDate("elapsed_date")).thenReturn(Date.valueOf(LocalDate.of(2025, 10, 01)));
        when(resultSet.getInt("quantity")).thenReturn(300);
        when(resultSet.getString("nome")).thenReturn("Tachipirina");
        when(resultSet.getString("tipologia")).thenReturn("Compresse");
        when(resultSet.getString("misura")).thenReturn("100mg");
        when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
        List<FieldData> fieldData = lottiDao.findAll();
        Assertions.assertEquals("Tachipirina", fieldData.get(0).getNome());
        verify(resultSet).getString("id");
        verify(resultSet).getDate("production_date");
        Assertions.assertAll(
                ()-> Assertions.assertEquals(Date.valueOf(LocalDate.of(2024, 10, 01)), fieldData.getFirst().getProduction_date()),
        ()->Assertions.assertEquals(Date.valueOf(LocalDate.of(2025, 10, 01)), fieldData.getFirst().getElapsed_date()),
                ()->   Assertions.assertEquals(300, fieldData.getFirst().getQuantity()),
                ()->Assertions.assertEquals("Tachipirina", fieldData.getFirst().getNome()),
                ()->Assertions.assertEquals("Compresse", fieldData.getFirst().getNome_tipologia()),
                ()-> Assertions.assertEquals("100mg", fieldData.getFirst().getUnit_misure()),
                ()->Assertions.assertEquals(6.50, fieldData.getFirst().getPrice()));


    }

    @Test
    void ValidFindByName() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getString(1)).thenReturn("Angelini", "Bayer", "Angelini");
        when(database.executeQuery(anyString())).thenReturn(resultSet);

        List<String> list = lottiDao.FindByFarmacoNameHaveLot();
        Assertions.assertEquals("Angelini", list.getFirst());


    }
    @Test
    void findByIds() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true,false);
        when(resultSet.getString("id")).thenReturn("aaa");
        when(resultSet.getDate("production_date")).thenReturn(Date.valueOf(LocalDate.of(2024, 10, 01)));
        when(resultSet.getDate("elapsed_date")).thenReturn(Date.valueOf(LocalDate.of(2025, 10, 01)));
        when(resultSet.getInt("quantity")).thenReturn(300);
        when(resultSet.getString("nome")).thenReturn("Tachipirina");
        when(resultSet.getString("tipologia")).thenReturn("Compresse");
        when(resultSet.getString("categoria")).thenReturn("Antibiotico");
        when(resultSet.getString("misura")).thenReturn("100mg");
        when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
        FieldData fieldData=lottiDao.findByIds(10,"aaa");
        Assertions.assertAll(
                ()->Assertions.assertEquals("Tachipirina",fieldData.getNome()),
                ()->Assertions.assertEquals("Compresse",fieldData.getNome_tipologia()),
                ()->Assertions.assertEquals("100mg",fieldData.getUnit_misure()));



    }


    @AfterEach
    public void tearDown() {
        database = null;
        lottiDao = null;
    }

    @Test
    public void ValidIntegrationTestingFind() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
            List<FieldData> lotti = lottiDao.findAll();
            System.out.println(lotti.getFirst().getLotto_id());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ValidIntegrationTestingFindSearch() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
            List<FieldData> lotti = lottiDao.findBySearch("Nome","a");
            System.out.println(lotti.size());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void ValidIntegrationFindIds() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
            FieldData fieldData=lottiDao.findByIds(60,"aaa");
            Assertions.assertNotNull(fieldData);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ValidIntegrationInsert() {
        Properties properties = null;
        LottiDao lottiDao = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
            lottiDao = new LottiDao(Database.getInstance(properties), "lotto");
            boolean value = lottiDao.insert(FieldData.FieldDataBuilder.getbuilder().setLotto_id("aaa").setTipologia(60).
                    setProduction_date(Date.valueOf(LocalDate.of(2024, 10, 01)))
                    .setElapsed_date(Date.valueOf(LocalDate.of(2025, 01, 9))).build());

            Assertions.assertTrue(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void findLotsByFarmacoName() throws SQLException {

        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("agk1", "agk4");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);
        List<String> value = lottiDao.findLotsByFarmacoName(anyString());
        Assertions.assertEquals("agk1", value.getFirst());
        Assertions.assertEquals("agk4", value.get(1));

    }

    @Test
    void findLotsbyPharma() throws SQLException {
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

        ArgumentCaptor<Integer> paramCaptor = ArgumentCaptor.forClass(Integer.class);
        when(database.execute_prepared_query(anyString())).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setInt(eq(1), paramCaptor.capture());
        when(preparedStatement.executeQuery()).thenAnswer(invocation -> {

          int argument = paramCaptor.getValue();
            ResultSet resultSet = Mockito.mock(ResultSet.class);
                    if (argument==1) {
                when(resultSet.next()).thenReturn(true, true, true, true, false);
                when(resultSet.getString("id")).thenReturn("agk1", "agk2", "agk3", "agk4");
                when(resultSet.getDate("production_date")).thenReturn(
                        Date.valueOf(LocalDate.of(2026, 10, 01)),
                        Date.valueOf(LocalDate.of(2024, 10, 01)),
                        Date.valueOf(LocalDate.of(2025, 10, 01)),
                        Date.valueOf(LocalDate.of(2023, 10, 01))
                );
                when(resultSet.getDate("elapsed_date")).thenReturn(
                        Date.valueOf(LocalDate.of(2027, 10, 01)),
                        Date.valueOf(LocalDate.of(2028, 10, 01)),
                        Date.valueOf(LocalDate.of(2029, 10, 01)),
                        Date.valueOf(LocalDate.of(2030, 10, 01)));
                when(resultSet.getString("nome")).thenReturn("Amuchina", "Moment", "Moment_Act", "Tachipirina");
                when(resultSet.getString("tipologia")).thenReturn("Supposte", "Compresse", "Compresse", "Compresse");
                when(resultSet.getString("misura")).thenReturn("100mg", "10gr", "30gr", "40gr");
                when(resultSet.getString("casa_farmaceutica")).thenReturn("Angelini");
                when(resultSet.getInt("farmaco")).thenReturn(1, 2, 3, 4);


            }
            return resultSet;
        });

        List<FieldData> fieldData = lottiDao.findLotsbyPharma(1);
        Assertions.assertEquals(4, fieldData.size());


    }

    @Test
    void buildQueryasParameterSearch() {


        String value=lottiDao.buildQueryasParameterSearch("Tipologia");
        System.out.println(value);

/*
        Assertions.assertDoesNotThrow(()-> {


                    CCJSqlParserUtil.parse(value);
        });*/




        //Assertions.assertEquals(lottiDao.getFindQueryAll()+" where nome  ILAKE ?   ",value);
    }

    @Test
    void TestquerySearch()throws  Exception{



            String value=lottiDao.buildQueryasParameterSearch("Nome");

            Statement statement=CCJSqlParserUtil.parse(value);

            if( statement instanceof Select select) {
                PlainSelect body = (PlainSelect) select.getSelectBody();
                Expression where = body.getWhere();

                if (where instanceof LikeExpression ilikeExpr) {
                    if (ilikeExpr.getRightExpression() instanceof JdbcParameter) {
                        // Replace ? with actual value, quoted
                        ilikeExpr.setRightExpression(new StringValue("a%"));
                    }
                    System.out.println(statement.toString());
                }
            }

    }



}






