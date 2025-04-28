package pharma.Controller.subpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import pharma.Handler.FarmaciaHandler;
import pharma.Handler.FarmaciaView;
import pharma.Model.FieldData;
import pharma.Storage.FileStorage;
import pharma.config.database.Database;
import pharma.config.TableUtility;
import pharma.config.Utility;
import pharma.dao.FarmaciaDao;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Farmacia implements Initializable {
    public TableView table_id;
    public Button btn_id_add;
    public Button search_id;
    public FarmaciaDao farmaciaDao;
    public ObservableList<FieldData> observableList;
    public Farmacia() {
        Properties properties = null;
        try {
            properties = FileStorage.getProperties_real(new ArrayList<>(Arrays.asList("host", "username", "password")), new FileReader("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.farmaciaDao = new FarmaciaDao(Database.getInstance(properties));
    }

    public void btn_action_add(ActionEvent actionEvent) {

     FarmaciaHandler farmaciaHandler=new FarmaciaHandler("Inserisci Farmacia", Collections.singletonList(farmaciaDao));
     farmaciaHandler.execute();

    }


    public void search_action(ActionEvent actionEvent) {
        FarmaciaView farmaciaView=new FarmaciaView("Opzioni di ricerca",farmaciaDao,observableList);
        farmaciaView.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utility.add_iconButton(btn_id_add,FontAwesomeSolid.PLUS_SQUARE);
        Utility.add_iconButton(search_id, FontAwesomeSolid.SEARCH);
        table_id.setVisible(true);
        table_id.getColumns().addAll(
                TableUtility.generate_column_int("ID","id"),
                TableUtility.generate_column_string("Ragione Sociale","anagrafica_cliente"),
                TableUtility.generate_column_string("Partita Iva","partita_iva"),
                TableUtility.generate_column_string("Via","street"),
                TableUtility.generate_column_int("Cap","cap"),
                TableUtility.generate_column_string("Comune","comune"),
                TableUtility.generate_column_string("Provincia","province"));




        observableList= FXCollections.observableArrayList();
        table_id.setItems(observableList);
        table_id.refresh();
        table_id.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);


    }
}
