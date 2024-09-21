package nguye.postcrunch.backend.controller;

import nguye.postcrunch.backend.api.DefaultApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController implements DefaultApi {

  @Override
  public ResponseEntity<String> fetchDemo() throws Exception {
    return ResponseEntity.ok("This is the demo page");
  }
}
