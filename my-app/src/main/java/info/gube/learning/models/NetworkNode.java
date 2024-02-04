package info.gube.learning.models;

import java.net.Inet4Address;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class NetworkNode {

  StringProperty addressProp;

  public NetworkNode(Inet4Address address){
    this.address = address;
    this.port = 0;
    this.latency = 0;

    this.addressProp = new SimpleStringProperty(address.getHostAddress());
  }
  
  // 192.168.0.1 for example
  private final Inet4Address address;

  // leave it for future uses
  private final int          port;

  // time to ping the address
  // ping goes to default port if it's not blocked!
  private final int          latency;

  public String getAddressProp(){
    return this.addressProp.get();
  }

  public void setAddressProp(String newVal) {
    this.addressProp.set(newVal);
  }

  public StringProperty addressProp() {
    return this.addressProp;
  }

}
