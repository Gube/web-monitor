package info.gube.learning.app;

import java.io.IOException;

import info.gube.learning.events.ScanningEvent;
import info.gube.learning.models.NetworkNode;
import info.gube.learning.models.NetworkNodesModel;
import info.gube.learning.views.ScannerDialog;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
      
        NetworkNodesModel model = new NetworkNodesModel();
        
        var pane = new GridPane();
        pane.setPadding( new Insets( 10 ));
        pane.setHgap(4);
        pane.setVgap(8);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow( Priority.ALWAYS );
        pane.getColumnConstraints().add(col1);
        pane.setGridLinesVisible(true);
        
        var dialog = new ScannerDialog();
        pane.add(dialog, 0, 1);

        dialog.addEventHandler(ScanningEvent.START_SCANNING, event -> {
          System.out.printf("Scanning %s", event.getSubnet());
          try {
            model.scan(event.getSubnet());
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

        TableView<NetworkNode> table = new TableView<>();
        table.setItems(model.getObservableNodes());

        TableColumn<NetworkNode, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("addressProp"));
        addressColumn.prefWidthProperty().bind(table.widthProperty().multiply(1.));
        table.getColumns().add(addressColumn);

        pane.add(table, 0,2);

        Scene scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.setTitle("First GUI app!");
        stage.setMinWidth(640);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
