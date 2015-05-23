package com.tim.one.bean;

/**
 * @author josdem
 * @understands A class who mapped project status as emum
 */

public enum ProjectStatusType {
  DEVELOPMENT(0, "Desarrollo"),
  REVIEWING(1, "Revisando"),
  CORRECTIONS(2, "Observaciones"),
  CORRECTED(3, "Corregido"),
  REJECTED(4, "Rechazado"),
  AUTORIZED(5, "Autorizado"),
  PRODUCTION(6, "Produccion"),
  PRESENTATION(7, "Presentacion"),
  CLOSED(8, "Finalizado"),
  REVIEW(9, "Revision"),
  PENDING(10, "Pendiente"),
  CLOSING(11, "Cerrando");

  private final String name;
  private final int code;

  private ProjectStatusType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static ProjectStatusType getTypeByCode(int code) {
    for (ProjectStatusType item : ProjectStatusType.values()) {
      if (item.code == code) {
        return item;
      }
    }
    return null;
  }
}
