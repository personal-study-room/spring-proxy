package com.example.proxy.app.v3;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

  private final OrderServiceV3 orderService;


  @GetMapping("/request")
  public String request(@RequestParam("itemId") String itemId) {
    orderService.orderItem(itemId);
    return "ok";
  }

  @GetMapping("/no-log")
  public String noLog() {
    return null;
  }
}
