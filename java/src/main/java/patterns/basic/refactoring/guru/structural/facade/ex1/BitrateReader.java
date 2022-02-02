package patterns.basic.refactoring.guru.structural.facade.ex1;

import static utils.PrintUtils.println;

public class BitrateReader {
    public static VideoFile read(VideoFile file, Codec codec) {
        println("BitrateReader: reading file...");
        return file;
    }

    public static VideoFile convert(VideoFile buffer, Codec codec) {
        println("BitrateReader: writing file...");
        return buffer;
    }
}