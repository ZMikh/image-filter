package ru.mikhailova.imageCrop.filter;

import org.springframework.stereotype.Component;
import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class GreyscaleFilter implements ImageFilter {

    @Override
    public BufferedImage filter(BufferedImage image, ImageParameters parameters) {
        if (!Boolean.TRUE.equals(parameters.getGrayscale())) {
            return image;
        }

        int width = image.getWidth();
        int length = image.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Color color = new Color(image.getRGB(i, j));
                double red = color.getRed() * 0.299;
                double green = color.getGreen() * 0.587;
                double blue = color.getBlue() * 0.114;
                Color changedColor = new Color((int) (red + green + blue), (int) (red + green + blue), (int) (red + green + blue));
                image.setRGB(i, j, changedColor.getRGB());
            }
        }
        return image;
    }
}
