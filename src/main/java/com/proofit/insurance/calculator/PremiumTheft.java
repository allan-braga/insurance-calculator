package com.proofit.insurance.calculator;

import com.proofit.insurance.entities.Policy;
import com.proofit.insurance.entities.PolicySubObject;
import com.proofit.insurance.entities.RiskType;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PremiumTheft implements Premium {

  private final Double coefficientTheftDefault;

  private final Double coefficientTheft;

  public PremiumTheft(
      @Value("${premium.coefficient.theft.default}") final double coefficientTheftDefault,
      @Value("${premium.coefficient.theft}") final double coefficientTheft) {
    this.coefficientTheftDefault = coefficientTheftDefault;
    this.coefficientTheft = coefficientTheft;
  }

  @Override
  public BigDecimal calculate(final Policy policy) {
    final BigDecimal sumInsuredTheft =
        policy.getObjects().stream()
            .flatMap(obj -> obj.getSubObjects().stream())
            .filter(subObj -> RiskType.THEFT.equals(subObj.getRiskType()))
            .map(PolicySubObject::getInsured)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (sumInsuredTheft.doubleValue() >= 15) {
      return sumInsuredTheft.multiply(BigDecimal.valueOf(coefficientTheft));
    }

    return sumInsuredTheft.multiply(BigDecimal.valueOf(coefficientTheftDefault));
  }
}
