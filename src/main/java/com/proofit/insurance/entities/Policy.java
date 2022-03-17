package com.proofit.insurance.entities;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Policy {

  @EqualsAndHashCode.Include
  private String number;
  private PolicyStatus status;
  private List<PolicyObject> objects;
}
