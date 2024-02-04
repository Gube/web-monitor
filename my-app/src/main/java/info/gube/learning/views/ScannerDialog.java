package info.gube.learning.views;

import info.gube.learning.events.ScanningEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ScannerDialog extends HBox{

  public ScannerDialog() {
    super();

    Button scanButton = new Button("Scan");
    TextField addressField = new TextField();

    addressField.setPromptText("IP4 address");

    scanButton.setOnMouseClicked(event -> {
      this.fireEvent(new ScanningEvent(ScanningEvent.START_SCANNING, addressField.getText()));
    });

    this.setPadding(new Insets(10));
    this.getChildren().add(scanButton);
    this.getChildren().add(addressField);
  }
}
