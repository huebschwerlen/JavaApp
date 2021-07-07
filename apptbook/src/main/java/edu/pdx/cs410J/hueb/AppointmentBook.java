package edu.pdx.cs410J.hueb;


import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class AppointmentBook extends AbstractAppointmentBook<Appointment> {


    private final String owner;
//    private List<Appointment> appointmentList = new ArrayList<>();
    private ArrayList<Appointment> aptList = new ArrayList<>();


    /**
     * Creates a new <code>AppointmentBook</code>
     */
    public AppointmentBook() {
        this.owner = null;
    }


    /**
     * Creates a new <code>AppointmentBook</code>
     *
     * @param owner
     *        The owner of the appointment book
     */
    public AppointmentBook(String owner) {
        this.owner = owner;
    }


    /**
     * Returns a <code>String</code> owner name for this
     * <code>AppointmentBook</code> .
     */
    @Override
    public String getOwnerName() {
        return this.owner;
    }


    /**
     * Returns a <code>ArrayList</code> of appointments for this
     * <code>AppointmentBook</code> .
     */
    @Override
    public Collection<Appointment> getAppointments() {
        return this.aptList;
    }


    /**
     * Adds a new <code>Appointment</code> for this
     * <code>AppointmentBook</code>
     *
     * @param apt
     *        An appointment of the appointment book
     */
    @Override
    public void addAppointment(Appointment apt) {
        this.aptList.add(apt);
    }


}
