package com.example.proxy.app.v2;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/v2")
@RequiredArgsConstructor
public class OrderControllerV2 {

  private final OrderServiceV2 orderService;
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
