package ru.mikhailova.imageCrop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mikhailova.imageCrop.domain.ImageParameters;
import ru.mikhailova.imageCrop.service.CropService;
import ru.mikhailova.imageCrop.service.FilterException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static ru.mikhailova.imageCrop.util.ImageConvertUtil.getBufferedImageFromFile;
import static ru.mikhailova.imageCrop.util.ImageConvertUtil.toByteArray;

@Controller
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "grayscale", required = false) Boolean grayscale,
                              @RequestParam(value = "cut_left", required = false) Integer cutLeft,
                              @RequestParam(value = "cut_above", required = false) Integer cutAbove,
                              @RequestParam(value = "cut_right", required = false) Integer cutRight,
                              @RequestParam(value = "cut_bottom", required = false) Integer cutBottom,
                              RedirectAttributes attributes) throws IOException {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("msg", "select image");
            return "redirect:/";
        }
        BufferedImage bufferedImageFromFile = getBufferedImageFromFile(file);
        if (Objects.isNull(bufferedImageFromFile)) {
            attributes.addFlashAttribute("msg", "Some problem with this photo");
            return "redirect:/";
        }

        ImageParameters imageParameters = ImageParameters.builder()
                .grayscale(grayscale)
                .cutBottom(cutBottom)
                .cutAbove(cutAbove)
                .cutRight(cutRight)
                .cutLeft(cutLeft)
                .build();

        try {
            BufferedImage upload = cropService.upload(bufferedImageFromFile, imageParameters);
            attributes.addFlashAttribute("croppedfile", Base64.encodeBase64String(toByteArray(upload)));
        } catch (FilterException e) {
            attributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/";
    }
}
