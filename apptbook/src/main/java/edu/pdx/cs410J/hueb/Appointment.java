package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  private final String description;
  private final String beginTime;
  private final String endTime;

  /**
   * Creates a new <code>Appointment</code>
   */
  public Appointment() {
    this.description = null;
    this.beginTime = null;
    this.endTime = null;
  }

  /**
   * Creates a new <code>Appointment</code>
   *
   * @param description
   *        The description of the appointment
   * @param beginTime
   *        The start or begin time of the appointment.
   * @param endTime
   *        The end time of the appointment.
   */
  public Appointment(String description, String beginTime, String endTime) {
//    super();
    this.description = description;
    this.beginTime = beginTime;
    this.endTime = endTime;
  }

  /**
   * Returns a <code>String</code> beginTime for this
   * <code>Appointment</code> .
   */
  @Override
  public String getBeginTimeString() {
    return this.beginTime;
  }

  /**
   * Returns a <code>String</code> endTime for this
   * <code>Appointment</code> .
   */
  @Override
  public String getEndTimeString() {
    return this.endTime;
  }

  /**
   * Returns a <code>String</code> description for this
   * <code>Appointment</code> .
   */
  @Override
  public String getDescription() {
    return this.description;
  }
}
