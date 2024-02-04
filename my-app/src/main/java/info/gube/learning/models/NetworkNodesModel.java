package info.gube.learning.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NetworkNodesModel {
  
  private final ListProperty<NetworkNode> nodesProperty;

  private final ObservableList<NetworkNode> observableNodes;


  public NetworkNodesModel() {
    observableNodes = FXCollections.observableArrayList();
    nodesProperty =  new SimpleListProperty<>(observableNodes);
  }

  /**
   * ms of timeout until we wait for answer
   */
  private final int timeout = 100; 

  /**
   * 
   * @param subnet is a `String` where all zeros in the end are defining a subnet to scan. <p>
   * Like 192.169.1.0 => we scan all between 192.169.1.0 and 192.169.1.255 <p>
   * 192.169.0.0 => 192.169.0.0 and 192.169.255.255
   * @throws IOException
   */
  public void scan(String subnet) throws IOException{
    // 0. Erase old results
    this.observableNodes.clear();

    // 1. Find the subnets
    var allSubnets = subnet.split("\\.");
    if (allSubnets.length != 4){
      throw new IOException("Wrong subnet for scanning were provided!");
    }
    // todo: add full verification for the subnet! 

    // 2. we know that only 4 blocks of numbers in its address
    // We need to find out the part of the subnet that is not '0'
    // in order to supplement the rest of it.
    StringBuilder fixedAddress = new StringBuilder();
    for (int i = 0; i < 3; i++){
      fixedAddress.append(allSubnets[i]);
      fixedAddress.append(".");
    }

    boolean isFullAddressProvided = false;
    if (!allSubnets[3].equals("0")) {
      isFullAddressProvided = true;
      fixedAddress.append(allSubnets[3]);
    }
  
    // meaning that we try to test the exact address
    // and not the subnet => just do it right away
    var address = fixedAddress.toString();
    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    if (isFullAddressProvided) {
      try {
        // executor.execute(new ThreadRunnerPing(address));
        var future = executor.submit( new CallablePingExec(address));
        var result = future.get();
        if (result.isReachable){
          observableNodes.add(new NetworkNode((Inet4Address)Inet4Address.getByName(result.getAddress())));
        }
      } catch (Exception e) {
        System.out.printf("UnknownHostException for address %s : %s", address, e.toString());
      }
    } else {
      ArrayList<CallablePingExec> listOfCalls = new ArrayList<>();

      //todo: at this moment we support only scan for the last block of IPv4 address a.k.a 192.168.11.0
      for (int i = 1; i < 10; i++){
        listOfCalls.add(new CallablePingExec(String.format("%s%d", address,i)));
      }
      
      List<Future<CallableResult>> futures = null;
      try {
        futures = executor.invokeAll(listOfCalls);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if ( futures != null) {
        for (var future : futures){
          if (future.isDone()){
            try {
              var result = future.get();
              if (result.isReachable){
                observableNodes.add(new NetworkNode((Inet4Address)Inet4Address.getByName(result.getAddress()) ));
              }
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
