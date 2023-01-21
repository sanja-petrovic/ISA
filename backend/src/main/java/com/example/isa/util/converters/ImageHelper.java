package com.example.isa.util.converters;

import com.example.isa.util.QrCodeGenerator;
import com.example.isa.util.TextFormattingHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageHelper {
    public static byte[] convertToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] bytes= byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        return bytes;
    }
}
