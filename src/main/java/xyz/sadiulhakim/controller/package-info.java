
@ApplicationModule(
        allowedDependencies = {
                "integration :: integration-gateway",
                "event",
                "student :: student-related-pojo",
                "pojo",
                "service"
        }
)
package xyz.sadiulhakim.controller;

import org.springframework.modulith.ApplicationModule;