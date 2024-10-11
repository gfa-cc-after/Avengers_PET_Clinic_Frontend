package com.avangers.backendapi.event;

import com.avangers.backendapi.DTOs.RegisterUserResponseDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import java.io.Serial;
@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -2685172945219633123L;
    private final RegisterUserResponseDTO userResponseDTO;

    public UserRegistrationEvent(RegisterUserResponseDTO userResponseDTO) {
        super(userResponseDTO);
        this.userResponseDTO = userResponseDTO;
    }
}
