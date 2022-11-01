package ru.mikhailova.imageCrop.filter;

import org.springframework.stereotype.Component;
import ru.mikhailova.imageCrop.domain.ImageParameters;
import ru.mikhailova.imageCrop.service.FilterException;

import java.awt.image.BufferedImage;

@Component
public class CropFilter implements ImageFilter {

    @Override
    public BufferedImage filter(BufferedImage image, ImageParameters parameters) {
        imageParamsValidate(parameters);
        int originalImageWidth = image.getWidth();
        int originalImageLength = image.getHeight();

        int x = parameters.getCutLeft() * originalImageWidth / 100;
        int y = parameters.getCutAbove() * originalImageLength / 100;
        int w = (100 - parameters.getCutRight() - parameters.getCutLeft()) * originalImageWidth / 100;
        int h = (100 - parameters.getCutBottom() - parameters.getCutAbove()) * originalImageLength / 100;


        return image.getSubimage(x, y, w, h);
    }

    private void imageParamsValidate(ImageParameters parameters) {
        if (parameters.getCutLeft() + parameters.getCutRight() >= 100) {
            throw new FilterException("sum of opposite cut sides can't be more than 100");
        }
        if (parameters.getCutAbove() + parameters.getCutBottom() >= 100) {
            throw new FilterException("sum of opposite cut sides can't be more than 100");
        }
        if (parameters.getCutLeft() < 0) {
            throw new FilterException("choose cut from any side in range of 0-100");
        }
        if (parameters.getCutAbove() < 0) {
            throw new FilterException("choose cut from any side in range of 0-100 ");
        }
        if (parameters.getCutBottom() < 0) {
            throw new FilterException("choose cut from any side in range of 0-100");
        }
        if (parameters.getCutRight() < 0) {
            throw new FilterException("choose cut from any side in range of 0-100 ");
        }
    }
}
