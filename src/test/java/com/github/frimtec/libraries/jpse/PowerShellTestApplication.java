package com.github.frimtec.libraries.jpse;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

/**
 * Demo and test application.
 */
class PowerShellTestApplication {

    public static void main(String[] args) {
        PowerShellExecutor executor = PowerShellExecutor.instance();
        System.out.println("PowerShell runtime version " +
                executor.version().orElseThrow(() -> new RuntimeException("No PowerShell runtime available")));

        System.out.println("Execute command: ");
        String output = executor.execute("Write-Host Hello PowerShell!").getStandardOutput();
        System.out.println(" output = " + output);

        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        System.out.println("Execute script as file: ");
        output = executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments).getStandardOutput();
        System.out.println(" output = " + output);

        System.out.println("Execute script from classpath: ");
        output = executor.execute(PowerShellTestApplication.class.getResourceAsStream("/test.ps1"), arguments).getStandardOutput();
        System.out.println(" output = " + output);
    }
}

