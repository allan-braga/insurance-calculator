package com.proofit.insurance.calculator;

import com.proofit.insurance.entities.Policy;
import com.proofit.insurance.entities.PolicySubObject;
import com.proofit.insurance.entities.RiskType;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PremiumFire implements Premium {

  private final Double coefficientFireDefault;

  private final Double coefficientFire;

  public PremiumFire(
      @Value("${premium.coefficient.fire.default}") final double coefficientFireDefault,
      @Value("${premium.coefficient.fire}") final double coefficientFire) {
    this.coefficientFireDefault = coefficientFireDefault;
    this.coefficientFire = coefficientFire;
  }

  @Override
  public BigDecimal calculate(Policy policy) {
    final BigDecimal sumInsuredFire =
        policy.getObjects().stream()
            .flatMap(obj -> obj.getSubObjects().stream())
            .filter(subObj -> RiskType.FIRE.equals(subObj.getRiskType()))
            .map(PolicySubObject::getInsured)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (sumInsuredFire.doubleValue() > 100) {
      return sumInsuredFire.multiply(BigDecimal.valueOf(coefficientFire));
    }

    return sumInsuredFire.multiply(BigDecimal.valueOf(coefficientFireDefault));
  }
}
