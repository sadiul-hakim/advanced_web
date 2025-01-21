package xyz.sadiulhakim;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTest {
    static ApplicationModules modules = ApplicationModules.of(Application.class);

    @Test
    void verifyModularity() {
        modules.verify();
    }

    @Test
    void doc(){
        new Documenter(modules).writeDocumentation();
    }
}
