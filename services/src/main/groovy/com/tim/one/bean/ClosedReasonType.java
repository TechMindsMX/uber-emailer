package com.tim.one.bean;

/**
 * @author josdem
 * @understands A class who mapped project closed reason as emum
 */

public enum ClosedReasonType {
  NOT_DELIVERY_PRODUCT(0, "No entrega del producto"), INCOMPLETE_DOCUMENTATION(1, "Documentación incompleta"), NOT_BE_REACHED(
      2, "No alcanzó el punto de equilibrio");

  private final String name;
  private final int code;

  private ClosedReasonType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static ClosedReasonType getTypeByCode(int code) {
    for (ClosedReasonType item : ClosedReasonType.values()) {
      if (item.code == code) {
        return item;
      }
    }
    return null;
  }

}