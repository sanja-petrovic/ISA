package com.example.isa.util;

import com.example.isa.model.Appointment;
import com.example.isa.model.BloodDonor;
import org.springframework.stereotype.Service;
import org.apache.commons.text.TextStringBuilder;

import java.util.Locale;

@Service
public class TextFormattingHelper {
    public static String formatQrCodeInformation(Appointment appointment) {
        TextStringBuilder textStringBuilder = new TextStringBuilder();
        textStringBuilder.append("Your personal ID: ");
        textStringBuilder.appendln(appointment.getBloodDonor().getPersonalId());
        textStringBuilder.append("Blood bank: ");
        textStringBuilder.appendln(appointment.getBloodBank().getTitle());
        textStringBuilder.append("Date and time: ");
        textStringBuilder.appendln(DateHelper.formatDate(appointment.getDateTime()));
        textStringBuilder.append("Status: ");
        textStringBuilder.appendln(appointment.getStatus().toString().toLowerCase(Locale.ROOT));

        return textStringBuilder.build();
    }

    public static String formatAppointmentDetails(Appointment appointment) {
        StringBuilder mailBodyBuilder = new StringBuilder();
        mailBodyBuilder.append("Your appointment at bank: ");
        mailBodyBuilder.append(appointment.getBloodBank().getTitle());
        mailBodyBuilder.append("has been successfully scheduled for ");
        mailBodyBuilder.append(appointment.getDateTime().toString());

        return mailBodyBuilder.toString();
    }
}
