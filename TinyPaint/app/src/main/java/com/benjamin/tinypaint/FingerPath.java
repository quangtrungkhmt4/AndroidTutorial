package com.benjamin.tinypaint;

import android.graphics.Path;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FingerPath {
    private int color;
    private boolean emboss;
    private boolean blur;
    private int strokeWidth;
    private Path path;
}
