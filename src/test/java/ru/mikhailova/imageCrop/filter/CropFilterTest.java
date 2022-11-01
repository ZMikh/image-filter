package ru.mikhailova.imageCrop.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mikhailova.imageCrop.domain.ImageParameters;
import ru.mikhailova.imageCrop.service.FilterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CropFilterTest {
    CropFilter cropFilter;
    BufferedImage bufferedImage;
    ImageParameters imageParameters;

    @BeforeEach
    void setUp() throws IOException {
        cropFilter = new CropFilter();
        URL resource = getClass().getResource("/images/testImage.jpg");
        bufferedImage = ImageIO.read(Objects.requireNonNull(resource));
        imageParameters = ImageParameters.builder()
                .cutRight(20)
                .cutBottom(20)
                .cutLeft(30)
                .cutAbove(40)
                .build();
    }

    @Test
    void testCropFilter() {
        BufferedImage filteredImage = cropFilter.filter(bufferedImage, imageParameters);
        assertPixelIsNotDark(filteredImage);
    }


    @Test
    void testWrongImageCutSizes() {
        ImageParameters wrongImageParams = ImageParameters.builder()
                .cutRight(0)
                .cutBottom(100)
                .cutLeft(100)
                .cutAbove(0)
                .build();

        assertThatThrownBy(() -> {
            cropFilter.filter(bufferedImage, wrongImageParams);
        }).isInstanceOf(FilterException.class);
    }

    private void assertPixelIsNotDark(BufferedImage image) {
        int width = image.getWidth();
        int length = image.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Color color = new Color(image.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                assertThat(red).isGreaterThan(55);
                assertThat(green).isGreaterThan(55);
                assertThat(blue).isGreaterThan(55);
            }
        }
    }
}
