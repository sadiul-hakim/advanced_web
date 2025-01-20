package xyz.sadiulhakim;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {
    static ApplicationModules modules = ApplicationModules.of(Application.class);

    @Test
    void verifyModularity() {
        modules.verify();
    }
}
