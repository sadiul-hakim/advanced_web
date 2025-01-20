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

### Testing - This testing makes sure you do not violate modularity rules.
Crate a test file then declare a ApplicationModules instance like this `static ApplicationModules modules = ApplicationModules.of(Application.class);` then
inside a test method call `modules.verify()`.

1. Named Interface
   `Create package-info.java file under sub packages and use @NamedInterface annotation to give a name or just use @NamedAnnotation on the type(class,record) name. NamedInterface make type(class,record) avilable outside`.
2. Open Application Modules
   `Create package-info.java file under module and use @ApplicationModule annotation to select type Open, but that makes all the files avilable outside of the module`
3. allowedDependencies
   `this paramater is used inside @ApplicationModule to specify which modules are allowed to be this modules dependency. After the = sign use {} to pass command seprated modules name and use {moduleName} :: {NameFilterface name} this syntax to pass alowed NamedInterfaces name.`