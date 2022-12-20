package com.example.isa.advice;

import java.util.Date;

import com.example.isa.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = NoCompletedQuestionnaire.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleNoCompletedQuestionnaireException(NoCompletedQuestionnaire ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                "Please fill out the questionnaire form first.",
                request.getDescription(false));
    }

    @ExceptionHandler(value = NewAppointmentTooSoonException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleNewAppointmentTooSoonException(NewAppointmentTooSoonException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                "Please wait 6 months between donations.",
                request.getDescription(false));
    }

    @ExceptionHandler(value = CantScheduleTwiceException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleCantScheduleTwiceException(CantScheduleTwiceException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                "You've already scheduled this appointment before.",
                request.getDescription(false));
    }

    @ExceptionHandler(value = UnableToCancelException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage handleUnableToCancelException(UnableToCancelException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                "Cancellations can only be made up to 24 hours before the scheduled appointment time.",
                request.getDescription(false));
    }
}