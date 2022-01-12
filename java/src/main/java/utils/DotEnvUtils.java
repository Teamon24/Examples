package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DotEnvUtils {

    public static Map<String, String> getVariables(final String resourcePath) throws FileNotFoundException
    {
        ClassLoader classLoader = DotEnvUtils.class.getClassLoader();

        URL resource = classLoader.getResource(resourcePath);
        Objects.requireNonNull(resource, String.format("Resource '%s' was not found", resourcePath));

        File file = new File(resource.getFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Map<String, String> envVariables = new HashMap<>();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("=");
                if (split.length == 2) {
                    envVariables.put(split[0], split[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envVariables;
    }

}
