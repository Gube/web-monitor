package com.mycompany.models;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.gube.learning.models.NetworkNodesModel;

public class NetworkNodesModelTest {
  
  NetworkNodesModel model;
  
  @BeforeEach
  void setup(){
    model = new NetworkNodesModel();
  }

  @Test
  void checkFullAddress(){
    try {
      model.scan("192.168.0.1");
    } catch (IOException e ){
      String.format("Error : %s", e.toString());
    }
  }

  @Test
  void checkRangeAddress(){
    try {
      model.scan("192.168.0.0");
    } catch (IOException e ){
      String.format("Error : %s", e.toString());
    }

    var found = model.getObservableNodes().size();
  }
}
