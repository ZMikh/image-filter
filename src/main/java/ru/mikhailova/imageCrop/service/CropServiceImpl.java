package ru.mikhailova.imageCrop.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class CropServiceImpl implements CropService {

    @Override
    public String upload(MultipartFile file, Boolean grayscale, Integer offsetLeft, Integer offsetBottom,
                         Integer offsetRight, Integer offsetAbove, RedirectAttributes attributes) throws IOException {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("msg", "select image");
            return "redirect:/";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        BufferedImage bufferedImageFromFile = getBufferedImageFromFile(file);
        if (Objects.isNull(bufferedImageFromFile)) {
            attributes.addFlashAttribute("msg", "Some problem with this photo");
            return "redirect:/";
        }

        BufferedImage bufferedImage = grayscaleImage(bufferedImageFromFile, grayscale);

        attributes.addFlashAttribute("msg", "image is uploaded " + fileName);

        BufferedImage bufferedImage1 = cropImage(bufferedImage, offsetLeft, offsetBottom, offsetRight, offsetAbove);
        attributes.addFlashAttribute("croppedfile", Base64.encodeBase64String(toByteArray(bufferedImage1)));

        attributes.addFlashAttribute("checkbox");
        return "redirect:/";
    }

    @Override
    public BufferedImage grayscaleImage(BufferedImage image, Boolean grayscale) {
        if(Boolean.TRUE.equals(grayscale)) {
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
    public BufferedImage cropImage(BufferedImage image, Integer offsetLeft, Integer offsetBottom, Integer offsetRight, Integer offsetAbove) {
        int originalImageWidth = image.getWidth();
        int originalImageLength = image.getHeight();

        int w = offsetRight *  originalImageWidth / 100;
        int h = offsetAbove * originalImageLength / 100;
        int x = offsetLeft * originalImageWidth / 100;
        int y = offsetBottom * originalImageLength / 100;

        return image.getSubimage(x, y, w, h);
    }

    @Override
    public BufferedImage getBufferedImageFromFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedInputStream input = new BufferedInputStream(inputStream);
        return ImageIO.read(input);
    }

    @Override
    public byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
