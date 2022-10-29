package ru.mikhailova.imageCrop.service;

import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface CropService {
    BufferedImage upload(BufferedImage bufferedImageFromFile, ImageParameters parameters) throws IOException;
    BufferedImage grayscaleImage(BufferedImage image, Boolean grayscale);
    BufferedImage cropImage(BufferedImage image, ImageParameters parameters);
}
