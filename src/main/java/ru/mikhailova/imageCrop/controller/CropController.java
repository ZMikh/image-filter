package ru.mikhailova.imageCrop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mikhailova.imageCrop.service.CropService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "grayscale", required = false) Boolean grayscale,
                              @RequestParam(value = "offset_left", required = false) Integer offsetLeft,
                              @RequestParam(value = "offset_bottom", required = false) Integer offsetBottom,
                              @RequestParam(value = "offset_right", required = false) Integer offsetRight,
                              @RequestParam(value = "offset_above", required = false) Integer offsetAbove,
                              RedirectAttributes attributes) throws IOException {

        return cropService.upload(file, grayscale, offsetLeft, offsetBottom, offsetRight, offsetAbove, attributes);
    }
}
