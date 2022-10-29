package ru.mikhailova.imageCrop.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mikhailova.imageCrop.domain.ImageParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreyscaleFilterTest {
    GreyscaleFilter greyScaleFilter;
    BufferedImage bufferedImage;
    ImageParameters parameters;

    @BeforeEach
    void setUp() throws IOException {
        greyScaleFilter = new GreyscaleFilter();
        URL resource = getClass().getResource("/images/testImage.jpg");
        bufferedImage = ImageIO.read(Objects.requireNonNull(resource));
        parameters = ImageParameters.builder()
                .grayscale(true)
                .build();
    }

    @Test
    void testFilter() {
        greyScaleFilter.filter(bufferedImage, parameters);
        assertPixelEquals(bufferedImage);
    }

    private void assertPixelEquals(BufferedImage image) {
        int width = image.getWidth();
        int length = image.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Color color = new Color(image.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                assertEquals(red, green);
                assertEquals(red, blue);
            }
        }
    }
}
