package com.amigoscode.bughunt.easy.bug31;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentBookTest {

    @Test
    void scheduleReturnsCorrectStart() {
        AppointmentBook book = new AppointmentBook("ada");
        Date original = new Date(1_700_000_000_000L);

        AppointmentBook.Appointment apt = book.schedule("standup", original, 30);

        assertThat(apt.start()).isEqualTo(new Date(1_700_000_000_000L));
    }

    @Test
    void mutatingReturnedDateDoesNotChangeAppointment() {
        AppointmentBook book = new AppointmentBook("ada");
        Date original = new Date(1_700_000_000_000L);
        AppointmentBook.Appointment apt = book.schedule("standup", original, 30);

        Date leaked = apt.start();
        leaked.setTime(0L);

        assertThat(apt.start()).isEqualTo(new Date(1_700_000_000_000L));
    }
}
