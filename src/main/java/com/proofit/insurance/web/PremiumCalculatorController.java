package com.proofit.insurance.web;

import com.proofit.insurance.entities.Policy;
import com.proofit.insurance.services.PremiumCalculator;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("premium-calculator/v1/")
@RequiredArgsConstructor
public class PremiumCalculatorController {

  private final PremiumCalculator calculator;

  @PostMapping("calculate")
  public BigDecimal calculate(@RequestBody Policy policy) {
    return calculator.calculate(policy);
  }
}
