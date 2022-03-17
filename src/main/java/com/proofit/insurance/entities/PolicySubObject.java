package com.proofit.insurance.entities;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PolicySubObject {

  @EqualsAndHashCode.Include
  private String name;
  private BigDecimal insured;
  private RiskType riskType;
}
