package ru.mikhailova.imageCrop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mikhailova.imageCrop.domain.ImageParameters;
import ru.mikhailova.imageCrop.filter.ImageFilter;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final List<ImageFilter> filters;

    @Override
    public BufferedImage upload(BufferedImage image, ImageParameters parameters) {
        filters.forEach(imageFilter -> imageFilter.filter(image, parameters));
        return image;
    }
}
