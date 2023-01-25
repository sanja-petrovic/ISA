package com.example.isa.util.qrCode;

import com.example.isa.model.Appointment;
import com.example.isa.util.formatters.TextFormatter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;

public class QrCodeGenerator {
    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateQRCodeForAppointment(Appointment appointment) throws Exception {
        String qrCodeInformation = TextFormatter.formatQrCodeInformation(appointment);
        return generateQRCodeImage(qrCodeInformation);
    }

}
