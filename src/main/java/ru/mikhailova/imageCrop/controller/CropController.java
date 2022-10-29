package ru.mikhailova.imageCrop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mikhailova.imageCrop.domain.ImageParameters;
import ru.mikhailova.imageCrop.mediator.Mediator;
import ru.mikhailova.imageCrop.service.CropService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "grayscale", required = false) Boolean grayscale,
                              @RequestParam(value = "offset_left", required = false) Integer offsetLeft,
                              @RequestParam(value = "offset_bottom", required = false) Integer offsetBottom,
                              @RequestParam(value = "offset_right", required = false) Integer offsetRight,
                              @RequestParam(value = "offset_above", required = false) Integer offsetAbove,
                              RedirectAttributes attributes) throws IOException {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("msg", "select image");
            return "redirect:/";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        BufferedImage bufferedImageFromFile = Mediator.getBufferedImageFromFile(file);
        if (Objects.isNull(bufferedImageFromFile)) {
            attributes.addFlashAttribute("msg", "Some problem with this photo");
            return "redirect:/";
        }

        ImageParameters imageParameters = ImageParameters.builder()
                .grayscale(grayscale)
                .offsetAbove(offsetAbove)
                .offsetBottom(offsetBottom)
                .offsetRight(offsetRight)
                .offsetLeft(offsetLeft)
                .build();


        BufferedImage upload = cropService.upload(bufferedImageFromFile, imageParameters);
        attributes.addFlashAttribute("msg", "image is uploaded " + fileName);


        attributes.addFlashAttribute("croppedfile", Base64.encodeBase64String(Mediator.toByteArray(upload)));

        attributes.addFlashAttribute("checkbox");
        return "redirect:/";
    }
}
