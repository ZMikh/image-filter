package ru.mikhailova.imageCrop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageParameters {
    private Boolean grayscale;
    private Integer offsetLeft;
    private Integer offsetBottom;
    private Integer offsetRight;
    private Integer offsetAbove;
}
