package com.proofit.insurance.entities;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PolicyObject {

  @EqualsAndHashCode.Include
  private String name;
  private List<PolicySubObject> subObjects;
}
