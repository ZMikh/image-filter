package ru.mikhailova.imageCrop.service;

import org.springframework.stereotype.Service;
import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class CropServiceImpl implements CropService {
    @Override
    public BufferedImage upload(BufferedImage bufferedImageFromFile, ImageParameters parameters) throws IOException {
        BufferedImage bufferedImage = grayscaleImage(bufferedImageFromFile, parameters.getGrayscale());
        return cropImage(bufferedImage, parameters);
    }

    @Override
    public BufferedImage grayscaleImage(BufferedImage image, Boolean grayscale) {
        if (Boolean.TRUE.equals(grayscale)) {
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
        }
        return image;
    }

    @Override
    public BufferedImage cropImage(BufferedImage image, ImageParameters parameters) {
        int originalImageWidth = image.getWidth();
        int originalImageLength = image.getHeight();

        int x = parameters.getOffsetLeft() * originalImageWidth / 100;
        int y = parameters.getOffsetBottom() * originalImageLength / 100;
        int w = (100 - parameters.getOffsetRight() - parameters.getOffsetLeft()) * originalImageWidth / 100;
        int h = (100 - parameters.getOffsetAbove() - parameters.getOffsetBottom()) * originalImageLength / 100;


        return image.getSubimage(x, y, w, h);
    }
}
