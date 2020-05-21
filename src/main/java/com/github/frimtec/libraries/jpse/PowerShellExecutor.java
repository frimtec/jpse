package com.github.frimtec.libraries.jpse;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public interface PowerShellExecutor {

    ExecutionResult execute(String command);

    ExecutionResult execute(Path script, Map<String, String> arguments);

    ExecutionResult execute(InputStream script, Map<String, String> arguments);

    static PowerShellExecutor instance() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("windows") ? new WindowsPowerShellExecutor() : new UnsupportedOsPowerShellExecutor(osName);
    }

}
