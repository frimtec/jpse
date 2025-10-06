# JPSE - Java PowerShell Executor

[![Maven Central][maven-central-shield]][maven-central]
[![License][license-shield]][license]

![Project Maintenance][maintenance-shield]
[![Code Coverage][codecov-shield]][codecov]

[![Build Status][build-status-shield]][build-status]
[![Deploy Status][deploy-status-shield]][deploy-status]

API to easily execute PowerShell commands and scripts from Java.
 
## Supported platforms
* Windows
* Linux (with installed PowerShell)
* MacOS (with installed PowerShell)

## Compatible JDK's
* 17
* 21
* 25

## Example
Call PowerShell commands or scripts like this:
```java
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
```

## Add dependency
To use jpse in your project you can add the dependency from [maven central][maven-central] to your software project management tool:

In Maven just add the following dependency to your pom.xml:
```xml
      <dependency>
        <groupId>com.github.frimtec</groupId>
        <artifactId>jpse</artifactId>
        <version>1.4.0</version>
      </dependency>
```

[maven-central-shield]: https://img.shields.io/maven-central/v/com.github.frimtec/jpse
[maven-central]: https://central.sonatype.com/artifact/com.github.frimtec/jpse
[maintenance-shield]: https://img.shields.io/maintenance/yes/2025.svg
[license-shield]: https://img.shields.io/github/license/frimtec/jpse.svg
[license]: https://opensource.org/licenses/Apache-2.0
[codecov-shield]: https://codecov.io/gh/frimtec/jpse/branch/master/graph/badge.svg?token=WHFQYWA0EA
[codecov]: https://codecov.io/gh/frimtec/jpse
[build-status-shield]: https://github.com/frimtec/jpse/workflows/Build/badge.svg
[build-status]: https://github.com/frimtec/jpse/actions?query=workflow%3ABuild
[deploy-status-shield]: https://github.com/frimtec/jpse/workflows/Deploy%20release/badge.svg
[deploy-status]: https://github.com/frimtec/jpse/actions?query=workflow%3A%22Deploy+release%22
