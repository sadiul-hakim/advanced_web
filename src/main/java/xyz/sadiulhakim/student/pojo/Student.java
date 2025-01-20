package xyz.sadiulhakim.student.pojo;

import org.springframework.modulith.NamedInterface;

@NamedInterface("student-related-pojo")
public record Student(
        int id,
        String name
) {
}
