package ru.mikhailova.imageCrop.service;

import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.image.BufferedImage;

public interface CropService {
    BufferedImage upload(BufferedImage bufferedImageFromFile, ImageParameters parameters);
}
