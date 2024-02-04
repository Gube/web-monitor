package info.gube.learning.events;

import java.util.List;

import info.gube.learning.models.NetworkNode;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Data;

@Data
public class ScanningEvent extends Event {

  private List<NetworkNode> reachableNodes = null;
  private String subnet = null;

  public static final EventType<ScanningEvent> START_SCANNING = new EventType<>("START_SCANNING");
  public static final EventType<ScanningEvent> STOP_SCANNING  = new EventType<>("STOP_SCANNING");
  public static final EventType<ScanningEvent> ERROR_SCANNING = new EventType<>("ERROR_SCANNING");

  public ScanningEvent(EventType<? extends Event> eventType, String subnet) {
    super(eventType);
    this.subnet = subnet;
  }
  
}
