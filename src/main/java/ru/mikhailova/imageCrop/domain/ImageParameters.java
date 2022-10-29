package ru.mikhailova.imageCrop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageParameters {
    private Boolean grayscale;
    private Integer offsetLeft;
    private Integer offsetAbove;
    private Integer offsetRight;
    private Integer offsetBottom;
}
