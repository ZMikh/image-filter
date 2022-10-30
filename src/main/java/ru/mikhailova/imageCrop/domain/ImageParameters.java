package ru.mikhailova.imageCrop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageParameters {
    private Boolean grayscale;
    private Integer cutLeft;
    private Integer cutAbove;
    private Integer cutRight;
    private Integer cutBottom;
}
