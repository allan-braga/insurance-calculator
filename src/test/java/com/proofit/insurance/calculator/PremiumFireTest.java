package com.proofit.insurance.calculator;

import com.proofit.insurance.entities.Policy;
import com.proofit.insurance.entities.PolicyObject;
import com.proofit.insurance.entities.PolicySubObject;
import com.proofit.insurance.entities.RiskType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PremiumFireTest {

  private static final double COEFFICIENT_FIRE_DEFAULT = 0.014;

  private static final double COEFFICIENT_FIRE = 0.024;

  private final PremiumFire premiumFire = new PremiumFire(COEFFICIENT_FIRE_DEFAULT, COEFFICIENT_FIRE);

  @Test
  @DisplayName("Calculate Premium Fire Default Coefficient")
  void calculatePremiumFireDefaultCoefficient() {
    BigDecimal insuredValue = BigDecimal.valueOf(100);
    final Policy policy = createPolicy(insuredValue);

    BigDecimal premium = premiumFire.calculate(policy);
    premium = premium.setScale(2, RoundingMode.HALF_EVEN);

    BigDecimal expectedPremium = insuredValue.multiply(BigDecimal.valueOf(COEFFICIENT_FIRE_DEFAULT));
    expectedPremium = expectedPremium.setScale(2, RoundingMode.HALF_EVEN);

    Assertions.assertNotNull(premium);
    Assertions.assertEquals(expectedPremium, premium);
  }

  @Test
  @DisplayName("Calculate Premium Fire Non Default Coefficient")
  void calculatePremiumFireNonDefaultCoefficient() {
    BigDecimal insuredValue = BigDecimal.valueOf(150);
    final Policy policy = createPolicy(insuredValue);

    BigDecimal premium = premiumFire.calculate(policy);
    premium = premium.setScale(2, RoundingMode.HALF_EVEN);

    BigDecimal expectedPremium = insuredValue.multiply(BigDecimal.valueOf(COEFFICIENT_FIRE));
    expectedPremium = expectedPremium.setScale(2, RoundingMode.HALF_EVEN);

    Assertions.assertNotNull(premium);
    Assertions.assertEquals(expectedPremium, premium);
  }

  public Policy createPolicy(final BigDecimal insuredValue) {
    Policy policy = new Policy();
    policy.setNumber("P0001");

    PolicyObject policyObject = new PolicyObject();
    policyObject.setName("HOUSE");

    PolicySubObject policySubObject1 = new PolicySubObject();
    policySubObject1.setName("TV");
    policySubObject1.setInsured(insuredValue);
    policySubObject1.setRiskType(RiskType.FIRE);

    policyObject.setSubObjects(List.of(policySubObject1));
    policy.setObjects(Collections.singletonList(policyObject));
    return policy;
  }
}
