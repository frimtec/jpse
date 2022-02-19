# JPSE - Java PowerShell Executor
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse)
[![codecov](https://codecov.io/gh/frimtec/jpse/branch/master/graph/badge.svg?token=WHFQYWA0EA)](https://codecov.io/gh/frimtec/jpse)
[![license](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[![Build](https://github.com/frimtec/jpse/workflows/Build/badge.svg)](https://github.com/frimtec/jpse/actions?query=workflow%3ABuild)
[![Deploy release](https://github.com/frimtec/jpse/workflows/Deploy%20release/badge.svg)](https://github.com/frimtec/jpse/actions?query=workflow%3A%22Deploy+release%22)

API to easily execute PowerShell commands and scripts from Java.
 
## Supported platforms
* Windows
* Linux (with installed PowerShell)

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
To use jpse in your project you can add the dependecy from [maven central](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse) to your software project management tool:

In Maven just add the following dependency to your pom.xml:
```xml
      <dependency>
        <groupId>com.github.frimtec</groupId>
        <artifactId>jpse</artifactId>
        <version>1.3.2</version>
      </dependency>
```
