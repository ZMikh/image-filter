package ru.mikhailova.imageCrop.filter;

import org.springframework.stereotype.Component;
import ru.mikhailova.imageCrop.domain.ImageParameters;

import java.awt.image.BufferedImage;

@Component
public class CropFilter implements ImageFilter {

    @Override
    public BufferedImage filter(BufferedImage image, ImageParameters parameters) {
        int originalImageWidth = image.getWidth();
        int originalImageLength = image.getHeight();

        int x = parameters.getOffsetLeft() * originalImageWidth / 100;
        int y = parameters.getOffsetAbove() * originalImageLength / 100;
        int w = (100 - parameters.getOffsetRight() - parameters.getOffsetLeft()) * originalImageWidth / 100;
        int h = (100 - parameters.getOffsetBottom() - parameters.getOffsetAbove()) * originalImageLength / 100;


        return image.getSubimage(x, y, w, h);
    }
}
