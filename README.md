1. Modulith
2. Event
3. Integration
4. Data Jdbc

# 1. Modulith

Under the root package `xyz.sadiulhakim` all the packages are called module in Spring Modulith.
And files directly under modules `not under any nested package` are called public api like: AppConfig
under config module is called public Api. Any `public` class under any package under any module can not be accessed from
other module by default
`package private, private, protected file are not accessable outside as they are still java types`.

## Ways to make them available outside

1. Named Interface `Create package-info.java file under sub packages and give them name`
2. Open Application Modules
   `Create package-info.java file under module and select type Open, but that makes all the files avilable`