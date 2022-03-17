package com.proofit.insurance.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.proofit.insurance.calculator.PremiumFire;
import com.proofit.insurance.calculator.PremiumTheft;
import com.proofit.insurance.entities.Policy;
import com.proofit.insurance.entities.PolicyObject;
import com.proofit.insurance.entities.PolicySubObject;
import com.proofit.insurance.entities.RiskType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PremiumCalculatorImplTest {

  private static final double COEFFICIENT_FIRE_DEFAULT = 0.014;
  private static final double COEFFICIENT_FIRE = 0.024;
  private static final double COEFFICIENT_THEFT_DEFAULT = 0.11;
  private static final double COEFFICIENT_THEFT = 0.05;

  private final PremiumFire premiumFire = new PremiumFire(COEFFICIENT_FIRE_DEFAULT, COEFFICIENT_FIRE);
  private final PremiumTheft premiumTheft = new PremiumTheft(COEFFICIENT_THEFT_DEFAULT, COEFFICIENT_THEFT);

  private final PremiumCalculatorImpl premiumCalculator =
      new PremiumCalculatorImpl(premiumFire, premiumTheft);

  @Test
  @DisplayName("Calculate Premium Should Match expected values")
  public void calculatePremiumShouldMatchExpectedValues() {
    BigDecimal expectedPremium = BigDecimal.valueOf(2.28);
    Policy policy = createPolicy(BigDecimal.valueOf(100), BigDecimal.valueOf(8.00));
    BigDecimal premium = premiumCalculator.calculate(policy);
    Assertions.assertEquals(expectedPremium, premium);
  }


  @Test
  @DisplayName("Calculate Premium Should Calculate Fire and Theft")
  public void calculateShouldCallFireAndTheftCalculators() {
    final PremiumFire premiumFire = mock(PremiumFire.class);
    final PremiumTheft premiumTheft = mock(PremiumTheft.class);
    final PremiumCalculatorImpl premiumCalculator = new PremiumCalculatorImpl(premiumFire, premiumTheft);

    when(premiumFire.calculate(any())).thenReturn(BigDecimal.ZERO);
    when(premiumTheft.calculate(any())).thenReturn(BigDecimal.ZERO);

    final Policy policy = createPolicy(BigDecimal.valueOf(100), BigDecimal.valueOf(8.00));
    final BigDecimal premium = premiumCalculator.calculate(policy);

    Assertions.assertNotNull(premium);
    verify(premiumFire, times(1)).calculate(policy);
    verify(premiumTheft, times(1)).calculate(policy);
  }

  private Policy createPolicy(final BigDecimal insuredValueFire, final BigDecimal insuredValueTheft) {
    Policy policy = new Policy();
    policy.setNumber("P0001");

    PolicyObject policyObject = new PolicyObject();
    policyObject.setName("HOUSE");

    PolicySubObject policySubObject1 = new PolicySubObject();
    policySubObject1.setName("TV");
    policySubObject1.setInsured(insuredValueFire);
    policySubObject1.setRiskType(RiskType.FIRE);

    PolicySubObject policySubObject2 = new PolicySubObject();
    policySubObject2.setName("RADIO");
    policySubObject2.setInsured(insuredValueTheft);
    policySubObject2.setRiskType(RiskType.THEFT);

    policyObject.setSubObjects(List.of(policySubObject1, policySubObject2));
    policy.setObjects(Collections.singletonList(policyObject));
    return policy;
  }
}
