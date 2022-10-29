package ru.mikhailova.imageCrop.filter;

import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.image.BufferedImage;

public interface ImageFilter {
    BufferedImage filter(BufferedImage image, ImageParameters parameters);
}
