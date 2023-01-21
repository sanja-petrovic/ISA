package com.example.isa.model;

/**
 * Scheduled: A future appointment that has been scheduled by a donor.
 * Not scheduled: An appointment that is available / has never been scheduled.
 * Completed: An appointment that has completed successfully.
 * Cancelled: An appointment that has been cancelled by a donor.
 * Missed: An appointment that has been missed by a donor.
 * Not held: An appointment that's never taken place.
 */
public enum AppointmentStatus {
    SCHEDULED,
    NOT_SCHEDULED,
    COMPLETED,
    CANCELLED,
    MISSED,
    NOT_HELD
}
