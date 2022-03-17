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
class PremiumTheftTest {

  private static final double COEFFICIENT_THEFT_DEFAULT = 0.11;

  private static final double COEFFICIENT_THEFT = 0.05;

  private final PremiumTheft premiumTheft = new PremiumTheft(COEFFICIENT_THEFT_DEFAULT, COEFFICIENT_THEFT);

  @Test
  @DisplayName("Calculate Premium Theft Default Coefficient")
  void calculatePremiumTheftDefaultCoefficient() {
    BigDecimal insuredValue = BigDecimal.valueOf(10);
    final Policy policy = createPolicy(insuredValue);

    BigDecimal premium = premiumTheft.calculate(policy);
    premium = premium.setScale(2, RoundingMode.HALF_EVEN);

    BigDecimal expectedPremium = insuredValue.multiply(BigDecimal.valueOf(COEFFICIENT_THEFT_DEFAULT));
    expectedPremium = expectedPremium.setScale(2, RoundingMode.HALF_EVEN);

    Assertions.assertNotNull(premium);
    Assertions.assertEquals(expectedPremium, premium);
  }

  @Test
  @DisplayName("Calculate Premium Theft Non Default Coefficient")
  void calculatePremiumTheftNonDefaultCoefficient() {
    BigDecimal insuredValue = BigDecimal.valueOf(150);
    final Policy policy = createPolicy(insuredValue);

    BigDecimal premium = premiumTheft.calculate(policy);
    premium = premium.setScale(2, RoundingMode.HALF_EVEN);

    BigDecimal expectedPremium = insuredValue.multiply(BigDecimal.valueOf(COEFFICIENT_THEFT));
    expectedPremium = expectedPremium.setScale(2, RoundingMode.HALF_EVEN);

    Assertions.assertNotNull(premium);
    Assertions.assertEquals(expectedPremium, premium);
  }

  private Policy createPolicy(final BigDecimal insuredValue) {
    Policy policy = new Policy();
    policy.setNumber("P0001");

    PolicyObject policyObject = new PolicyObject();
    policyObject.setName("HOUSE");

    PolicySubObject policySubObject1 = new PolicySubObject();
    policySubObject1.setName("TV");
    policySubObject1.setInsured(insuredValue);
    policySubObject1.setRiskType(RiskType.THEFT);

    policyObject.setSubObjects(List.of(policySubObject1));
    policy.setObjects(Collections.singletonList(policyObject));
    return policy;
  }
}
