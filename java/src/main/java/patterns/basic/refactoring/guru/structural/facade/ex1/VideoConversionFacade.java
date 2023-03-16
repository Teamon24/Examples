package patterns.basic.refactoring.guru.structural.facade.ex1;

import utils.PrintUtils;

import java.io.File;

import static java.lang.System.out;

public class VideoConversionFacade {
    public File convertVideo(String fileName, String format) {
        System.out.println("VideoConversionFacade: conversion started.");
        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec = getCodec(format);
        File result = convert(file, sourceCodec, destinationCodec);
        System.out.println("VideoConversionFacade: conversion completed.");
        return result;
    }

    private File convert(final VideoFile file,
                         final Codec sourceCodec,
                         final Codec destinationCodec)
    {
        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        File result = new AudioMixer().fix(intermediateResult);
        return result;
    }

    private Codec getCodec(final String format) {
        Codec destinationCodec;
        if (format.equals("mp4")) {
            destinationCodec = new OggCompressionCodec();
        } else {
            destinationCodec = new MPEG4CompressionCodec();
        }
        return destinationCodec;
    }
}