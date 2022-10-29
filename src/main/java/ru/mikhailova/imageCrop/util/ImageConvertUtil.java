package ru.mikhailova.imageCrop.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageConvertUtil {
    public static BufferedImage getBufferedImageFromFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedInputStream input = new BufferedInputStream(inputStream);
        return ImageIO.read(input);
    }

    public static byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
