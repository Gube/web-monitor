package info.gube.learning.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallableResult {
  String address;
  Boolean isReachable;
}
