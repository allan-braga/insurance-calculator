package com.proofit.insurance.services;

import com.proofit.insurance.calculator.PremiumFire;
import com.proofit.insurance.calculator.PremiumTheft;
import com.proofit.insurance.entities.Policy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PremiumCalculatorImpl implements PremiumCalculator {

  private final PremiumFire premiumFire;

  private final PremiumTheft premiumTheft;

  @Override
  public BigDecimal calculate(final Policy policy) {
    final BigDecimal premium = new BigDecimal(0);
    return premium
        .add(premiumFire.calculate(policy))
        .add(premiumTheft.calculate(policy))
        .setScale(2, RoundingMode.HALF_EVEN);
  }
}
