package patterns.basic.refactoring.guru.structural.facade.ex1;

import static utils.PrintUtils.println;

public class CodecFactory {
    public static Codec extract(VideoFile file) {
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        }
        else {
            println("CodecFactory: extracting ogg audio...");
            return new OggCompressionCodec();
        }
    }
}