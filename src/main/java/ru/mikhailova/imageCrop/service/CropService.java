package ru.mikhailova.imageCrop.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface CropService {
    String upload(MultipartFile file, Boolean grayscale, Integer offsetLeft, Integer offsetBottom, Integer offsetRight,
                  Integer offsetAbove, RedirectAttributes attributes) throws IOException;
    BufferedImage grayscaleImage(BufferedImage image, Boolean grayscale);
    BufferedImage cropImage(BufferedImage image, Integer offsetLeft, Integer offsetBottom, Integer offsetRight, Integer offsetAbove);

    BufferedImage getBufferedImageFromFile(MultipartFile file) throws IOException;
    byte[] toByteArray(BufferedImage image) throws IOException;
}
