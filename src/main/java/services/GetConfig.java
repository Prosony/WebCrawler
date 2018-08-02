package services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class GetConfig {
    private static GetConfig instance = new GetConfig();
    public static GetConfig getInstance(){
        return instance;
    }

    private int maxParallelRequest;
    private int requestDelayMs;
    private boolean isDebug;
    private String rootPath;
    private String username;
    private String password;

    private GetConfig() {
        try {
            Properties properties = new Properties();
            rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            String globalConfigPath = rootPath + "global-config.properties";
            properties.load(new FileInputStream(globalConfigPath));
            maxParallelRequest = Integer.parseInt(properties.getProperty("MAX_PARALLEL_REQUEST"));
            requestDelayMs = Integer.parseInt(properties.getProperty("REQUEST_DELAY_MS"));
            isDebug = Boolean.valueOf(properties.getProperty("IS_DEBUG"));
            username = properties.getProperty("USERNAME");
            password = properties.getProperty("PASSWORD");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getMaxParallelRequest() {
        return maxParallelRequest;
    }
    public int getRequestDelayMs() {
        return requestDelayMs;
    }
    public String getRootPath(){
        return rootPath;
    }
    public boolean isDEBUG() {
        return isDebug;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

