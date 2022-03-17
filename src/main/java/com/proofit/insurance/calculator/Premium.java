package com.proofit.insurance.calculator;

import com.proofit.insurance.entities.Policy;
import java.math.BigDecimal;

public interface Premium {

  BigDecimal calculate(Policy policy);

}
