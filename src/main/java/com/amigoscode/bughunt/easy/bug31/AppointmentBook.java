package com.amigoscode.bughunt.easy.bug31;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentBook {

    public static class Appointment {
        private final String subject;
        private final Date start;
        private final int durationMinutes;

        public Appointment(String subject, Date start, int durationMinutes) {
            this.subject = subject;
            this.start = start;
            this.durationMinutes = durationMinutes;
        }

        public String subject() {
            return subject;
        }

        public Date start() {
            return start;
        }

        public int durationMinutes() {
            return durationMinutes;
        }
    }

    private final String owner;
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentBook(String owner) {
        this.owner = owner;
    }

    public Appointment schedule(String subject, Date start, int durationMinutes) {
        Appointment a = new Appointment(subject, start, durationMinutes);
        appointments.add(a);
        return a;
    }

    public List<Appointment> all() {
        return List.copyOf(appointments);
    }

    public String owner() {
        return owner;
    }

    public int count() {
        return appointments.size();
    }
}
