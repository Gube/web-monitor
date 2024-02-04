package info.gube.learning.models;

import java.io.IOException;
import java.util.concurrent.Callable;

public class CallablePingExec implements Callable<CallableResult> {
  private String address;
  public boolean isReachable = false;

  public CallablePingExec(String address){
    this.address = address;
  }

  @Override
  public CallableResult call() throws Exception {
    Process p1;
    int returnVal = 2;
    try {
      p1 = java.lang.Runtime.getRuntime().exec(String.format("ping -c 1 %s", address));
      returnVal = p1.waitFor();
    } 
    catch (IOException e){
      e.printStackTrace();;
    }
    catch (InterruptedException e){
      e.printStackTrace();
    }
    return new CallableResult(address, returnVal == 0);
  }
}
