package com.github.frimtec.libraries.jpse;

import java.nio.file.Path;

class LinuxPowerShellExecutor extends AbstractPowerShellExecutor {

    private static final String POWER_SHELL_CMD = "pwsh";
    private static final String PARAM_QUOTE = "";

    LinuxPowerShellExecutor(Path tempPath) {
        super(tempPath, POWER_SHELL_CMD, PARAM_QUOTE);
    }
}
