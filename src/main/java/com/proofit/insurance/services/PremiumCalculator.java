package com.proofit.insurance.services;

import com.proofit.insurance.entities.Policy;
import java.math.BigDecimal;

public interface PremiumCalculator {

  BigDecimal calculate(Policy policy);

}
