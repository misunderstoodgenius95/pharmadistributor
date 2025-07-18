package pharma.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pharma.Model.FieldData;
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


        @Nested
     public class FindALl {
            private List<FieldData> list;

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
                Assertions.assertEquals("Via Corso Cavour 42", list.getFirst().getStreet());
            }

            @Test
            void ValidComune() {
                Assertions.assertEquals("Roma", list.getFirst().getComune());
            }

            @Test
            void ValidLat() {
                Assertions.assertEquals(38.1257128, list.getFirst().getLatitude());

            }

            @Test
            void ValidLong() {
                Assertions.assertEquals(14.7595248, list.getFirst().getLongitude());
            }
        }

            @Test
            public void ValidInsert() throws SQLException {
                    FieldData insert = FieldData.FieldDataBuilder.getbuilder().
                            setId(1).
                            setNome("A22").
                            setStreet("Via regina celi").
                            setComune("Roma").
                            setLatitude(38.1257128).
                            setLongitude(14.7595248).build();

                   when(preparedStatement.executeUpdate()).thenReturn(1);
                    when(database.execute_prepared_query(Mockito.anyString())).thenReturn(preparedStatement);
                    Assertions.assertTrue(magazzinoDao.insert(insert));

            }

            @Test
            public void IntegartionInsert(){
                Properties properties = null;

                try {
                    FieldData insert = FieldData.FieldDataBuilder.getbuilder().
                            setId(1).
                            setNome("A22").
                            setStreet("Via regina celi").
                            setComune("Roma").
                            setLatitude(38.1257128).
                            setLongitude(14.7595248).build();
                    properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
                    magazzinoDao=new MagazzinoDao(Database.getInstance(properties));
                    Assertions.assertTrue(magazzinoDao.insert(insert));


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


