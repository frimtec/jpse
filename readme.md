# JPSE - Java PowerShell Executor
[![Build Status](https://travis-ci.org/frimtec/jpse.svg?branch=master)](https://travis-ci.org/frimtec/jpse) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse) 
[![Coverage Status](https://coveralls.io/repos/github/frimtec/jpse/badge.svg?branch=master)](https://coveralls.io/github/frimtec/jpse?branch=master)
[![license](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

API to easily execute PowerShell commands and scripts from Java.
 
## Supported platforms
* Windows

## Example
Call PowerShell commands or scripts like this:
```java
  PowerShellExecutor executor = PowerShellExecutor.instance(null);

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
```

## Add dependency
To use jpse in your project you can add the dependecy from [maven central](https://maven-badges.herokuapp.com/maven-central/com.github.frimtec/jpse) to your software project management tool:

In Maven just add the following dependency to your pom.xml:
```xml
      <dependency>
        <groupId>com.github.frimtec</groupId>
        <artifactId>jpse</artifactId>
        <version>1.1.0</version>
      </dependency>
```
