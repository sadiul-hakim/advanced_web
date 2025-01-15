package xyz.sadiulhakim.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import java.io.Serial;

@Getter
public class CustomEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 889202626288526113L;

    private final String msg;
    public CustomEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

}
