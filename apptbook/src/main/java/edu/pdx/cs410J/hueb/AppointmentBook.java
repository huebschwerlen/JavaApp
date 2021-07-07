package edu.pdx.cs410J.hueb;


import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class AppointmentBook extends AbstractAppointmentBook<Appointment> {


    private final String owner;
    private List<Appointment> appointmentList = new ArrayList<>();
    private ArrayList<Appointment> aptList = new ArrayList<>();

    public AppointmentBook() {
        this.owner = null;
    }

    public AppointmentBook(String owner) {
        this.owner = owner;
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return this.aptList;
    }

    @Override
    public void addAppointment(Appointment apt) {
        this.aptList.add(apt);
    }


}
