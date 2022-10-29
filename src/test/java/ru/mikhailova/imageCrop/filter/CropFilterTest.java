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

import static org.assertj.core.api.Assertions.assertThat;

public class CropFilterTest {
    CropFilter cropFilter;
    BufferedImage bufferedImage;
    ImageParameters imageParameters;

    Color color;

    @BeforeEach
    void setUp() throws IOException {
        cropFilter = new CropFilter();
        URL resource = getClass().getResource("/images/testImage.jpg");
        bufferedImage = ImageIO.read(Objects.requireNonNull(resource));
        imageParameters = ImageParameters.builder()
                .offsetRight(20)
                .offsetBottom(20)
                .offsetLeft(30)
                .offsetAbove(40)
                .build();
    }

    @Test
    void testCropFilter() {
        BufferedImage filteredImage = cropFilter.filter(bufferedImage, imageParameters);
        assertPixelEquals(filteredImage);
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
                assertThat(red).isGreaterThan(55);
                assertThat(green).isGreaterThan(55);
                assertThat(blue).isGreaterThan(55);
            }
        }
    }
}
