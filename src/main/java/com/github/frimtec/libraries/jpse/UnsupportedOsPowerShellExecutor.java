package com.github.frimtec.libraries.jpse;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

final class UnsupportedOsPowerShellExecutor implements PowerShellExecutor {
    private final String osName;

    UnsupportedOsPowerShellExecutor(String osName) {
        this.osName = osName;
    }

    @Override
    public ExecutionResult execute(String command) {
        throw new UnsupportedOperationException("Not supported on OS " + this.osName);
    }

    @Override
    public ExecutionResult execute(Path script, Map<String, String> arguments) {
        throw new UnsupportedOperationException("Not supported on OS " + this.osName);
    }

    @Override
    public ExecutionResult execute(InputStream script, Map<String, String> arguments) {
        throw new UnsupportedOperationException("Not supported on OS " + this.osName);
    }
}
