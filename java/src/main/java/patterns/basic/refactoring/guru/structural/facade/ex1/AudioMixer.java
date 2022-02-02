package patterns.basic.refactoring.guru.structural.facade.ex1;

import java.io.File;

import static utils.PrintUtils.println;

public class AudioMixer {
    public File fix(VideoFile result){
        println("AudioMixer: fixing audio...");
        return new File("tmp");
    }
}