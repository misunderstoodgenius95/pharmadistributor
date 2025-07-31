package pharma.dao;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
import pharma.Model.Warehouse;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.formula.RecommendSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MagazzinoDaoTest {
    @Mock
    private Database database;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private MagazzinoDao magazzinoDao;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
         magazzinoDao=new MagazzinoDao(database);




    }

    @Test
    void findById() throws SQLException {
        Point point = new Point(38.1257128, 14.7595248);
        PGgeometry geom = new PGgeometry(point);
        when(resultSet.getObject("location")).thenReturn((PGgeometry) geom);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nome")).thenReturn("a11");
        when(resultSet.getString("address")).thenReturn("Via Corso Cavour 42");
        when(resultSet.getString("comune")).thenReturn("Roma");

        when(resultSet.next()).thenReturn(true, false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
     Warehouse magazzino=magazzinoDao.findById(1);
       assertThat(magazzino).satisfies(p-> {
                   assertThat(p.getId()).isEqualTo(1);
                   assertThat(p.getNome()).isEqualTo("a11");
                   assertThat(p.getAddress()).isEqualTo("Via Corso Cavour 42");
                   assertThat(p.getComune()).isEqualTo("Roma");
                   assertThat((Point)p.getpGgeometry().getGeometry()).satisfies(point1->{
                   assertThat(point1.getX()).isEqualTo(point.getX());
                   assertThat(point1.getY()).isEqualTo(point.getY());
                   });
               });



    }


    @Nested
     public class FindALl {
            private List<Warehouse> list;

            @BeforeEach
            public void setUp() throws SQLException {
                when(resultSet.getInt("id")).thenReturn(1);
                when(resultSet.getString("nome")).thenReturn("a11");
                when(resultSet.getString("address")).thenReturn("Via Corso Cavour 42");
                when(resultSet.getString("comune")).thenReturn("Roma");
                when(resultSet.getDouble("longitude")).thenReturn(38.1257128);
                when(resultSet.getDouble("latitude")).thenReturn(14.7595248);
                when(resultSet.next()).thenReturn(true, false);
                when(database.executeQuery(Mockito.anyString())).thenReturn(resultSet);
                list = magazzinoDao.findAll();


            }

            @Test
            void ValidId() throws SQLException {

                Assertions.assertEquals(1, list.getFirst().getId());
            }

            @Test
            public void ValidNome() {
                Assertions.assertEquals("a11", list.getFirst().getNome());
            }

            @Test
            void ValidStreet() {
                Assertions.assertEquals("Via Corso Cavour 42", list.getFirst().getAddress());
            }

            @Test
            void ValidComune() {
                Assertions.assertEquals("Roma", list.getFirst().getComune());
            }

            @Test
            void ValidLat() {
                Point point=(Point)list.getFirst().getpGgeometry().getGeometry();
                Assertions.assertEquals(38.1257128,point.getX());

            }

            @Test
            void ValidLng() {
                Point point=(Point)list.getFirst().getpGgeometry().getGeometry();
                Assertions.assertEquals(14.7595248, point.getY());
            }
        }

            @Test
            public void ValidInsert() throws SQLException {
        Point point=new Point(38.1257128,14.7595248);
        PGgeometry pGgeometry=new PGgeometry(point);
        Warehouse warehouse=new Warehouse();
                            warehouse.setId(1);
                            warehouse.setNome("A22");
                            warehouse.setAddress("Via regina celi");
                            warehouse.setComune("Roma");
                            warehouse.setpGgeometry(pGgeometry);
                   when(preparedStatement.executeUpdate()).thenReturn(1);
                   when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
                   Assertions.assertTrue(magazzinoDao.insert(warehouse));

            }

            @Test
            public void IntegartionInsert(){
                Properties properties = null;
                Warehouse warehouse;
                try {
                    Point point=new Point(38.1257128,14.7595248);
                    PGgeometry pGgeometry=new PGgeometry(point);
               warehouse=new Warehouse();
                    warehouse.setId(1);
                    warehouse.setNome("A22");
                    warehouse.setAddress("Via regina celi");
                    warehouse.setComune("Roma");
                    warehouse.setpGgeometry(pGgeometry);


                    properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                    magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
                    Assertions.assertTrue(magazzinoDao.insert(warehouse));


                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }



















   /*



        Assertions.assertEquals(14.7595248,list.getFirst().getLatitude());*/


/*      verify(resultSet.getInt(1),Mockito.times(1));
        verify(resultSet.getString(2),times(2));
        verify(database.executeQuery(Mockito.anyString()), times(1));
        verify(resultSet.getString(2),times(1));
        verify(resultSet.getString(3),times(1));
        verify(resultSet.getString(4),times(1));*/
        }


