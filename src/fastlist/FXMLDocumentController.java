package fastlist;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Code
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfId;
    @FXML
    private TableColumn<Shop, Integer> colId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfKategorie;
    @FXML
    private TextField tfMarke;
    @FXML
    private DatePicker tfDatum;
    @FXML
    private TextField tfPreis;
    @FXML
    private TextField tfOrt;
    @FXML
    private TableColumn<Shop, String> colName;
    @FXML
    private TableColumn<Shop, String> colKategorie;
    @FXML
    private TableColumn<Shop, String> colMarke;
    @FXML
    private TableColumn<Shop, LocalDate> colDatum;
    @FXML
    private TableColumn<Shop, Integer> colPreis;
    @FXML
    private TableColumn<Shop, String> colOrt;
    @FXML
    private TableView<Shop> tvShops;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        showLists();
      
    }    
public Connection getConnection(){
        Connection conn;
        try{
             Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/fastlist","root","");
            return conn; 
        }catch(Exception ex){
         System.out.println("Error: " + ex.getMessage());
         return null;
    }
}

public ObservableList<Shop> getShopList() {
    ObservableList<Shop> shopList = FXCollections.observableArrayList();
    Connection conn = getConnection();
    String query = "SELECT * FROM shop";
    Statement st;
    ResultSet rs;

    try {
        st = conn.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            System.out.println("Geladen: " + rs.getInt("id") + " - " + rs.getString("produktname"));
            Shop shop = new Shop(
                rs.getInt("id"),
                rs.getString("produktname"),
                rs.getString("kategorie"),
                rs.getString("marke"),
                rs.getDate("ablaufdatum").toLocalDate(),
                rs.getInt("verkaufspreis"),
                rs.getString("lagerort")
            );
            shopList.add(shop);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return shopList;
}

public void showLists(){
      
        ObservableList<Shop> list= getShopList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Shop, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Shop, String>("produktname"));
        colKategorie.setCellValueFactory(new PropertyValueFactory<Shop, String>("kategorie"));
        colMarke.setCellValueFactory(new PropertyValueFactory<Shop, String>("marke"));
        colDatum.setCellValueFactory(new PropertyValueFactory<Shop, LocalDate>("ablaufsdatum"));
        colPreis.setCellValueFactory(new PropertyValueFactory<Shop, Integer>("verkaufspreis"));
        colOrt.setCellValueFactory(new PropertyValueFactory<Shop, String>("lagerort"));
        
        
        tvShops.setItems(list);
    }
private void insertRecord() {
    String query;

    if (tfId.getText().isEmpty()) {
        query = "INSERT INTO shop (produktname, kategorie, marke, ablaufdatum, verkaufspreis, lagerort) VALUES ('" 
                + tfName.getText() + "','"
                + tfKategorie.getText() + "','"
                + tfMarke.getText() + "','"
                + tfDatum.getValue() + "',"
                + tfPreis.getText() + ",'"
                + tfOrt.getText() + "')";
    } else {
        query = "INSERT INTO shop (id, produktname, kategorie, marke, ablaufdatum, verkaufspreis, lagerort) VALUES ("
                + tfId.getText() + ",'"
                + tfName.getText() + "','"
                + tfKategorie.getText() + "','"
                + tfMarke.getText() + "','"
                + tfDatum.getValue() + "',"
                + tfPreis.getText() + ",'"
                + tfOrt.getText() + "')";
    }

    executeQuery(query);
    showLists(); 
}
private void updateButton() {
    String query = "UPDATE shop SET produktname = '" + tfName.getText() + "', kategorie = '" + tfKategorie.getText() + 
                   "', marke = '" + tfMarke.getText() + "', ablaufdatum = '" + tfDatum.getValue() + 
                   "', verkaufspreis = " + tfPreis.getText() + ", lagerort = '" + tfOrt.getText() + "' WHERE id = " + tfId.getText();
    executeQuery(query);
    showLists(); 
}


    @FXML
private void handleMouseAction(MouseEvent event) {
    Shop shop = tvShops.getSelectionModel().getSelectedItem();
    tfId.setText("" + shop.getId());  //
    tfName.setText(shop.getProduktname());
    tfKategorie.setText(shop.getKategorie());
    tfMarke.setText(shop.getMarke());
    tfDatum.setValue(shop.getAblaufdatum());  
    tfPreis.setText("" + shop.getVerkaufspreis());
    tfOrt.setText(shop.getLagerort());
}
        

     private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st= conn.createStatement();
            st.executeUpdate(query);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
private void handleBtnInsertAction(ActionEvent event) {
    insertRecord(); 

    Alert insertAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK);
    insertAlert.setTitle("Shop -> Datenbank");
    insertAlert.setContentText("Erfolgreich hinzugefügt!!!");
    insertAlert.showAndWait();

   
    tfId.setText("");
    tfName.setText("");
    tfKategorie.setText("");
    tfMarke.setText("");
    tfDatum.setValue(null); 
    tfPreis.setText("");
    tfOrt.setText("");
}

    
@FXML
    private void handleBtnUpdateAction(ActionEvent event) {
        updateButton();
             Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION.NONE,"Confirm",ButtonType.OK);
             updateAlert.setTitle("Update -> Datenbank");
           updateAlert.setContentText("Erfolgreich erledigt!!!");
           updateAlert.showAndWait();
           
         
           
    }}