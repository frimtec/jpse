import com.github.frimtec.libraries.jpse.PowerShellExecutor;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

/**
 * Demo and test application.
 */
class PowerShellTestApplication {

    public static void main(String[] args) throws IOException {
        PowerShellExecutor executor = PowerShellExecutor.instance();

        System.out.println("Execute command: ");
        String output = executor.execute("Write-Host Hello PowerShell!").getStandartOutput();
        System.out.println(" output = " + output);

        Map<String, String> arguments = Collections.singletonMap("name", "PowerShell");

        System.out.println("Execute scipt as file: ");
        output = executor.execute(Paths.get(".\\src\\test\\resources\\test.ps1"), arguments).getStandartOutput();
        System.out.println(" output = " + output);

        System.out.println("Execute scipt from classpath: ");
        output = executor.execute(PowerShellTestApplication.class.getResourceAsStream("/test.ps1"), arguments).getStandartOutput();
        System.out.println(" output = " + output);
    }
}

