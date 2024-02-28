package vn.edu.iuh.fit.jdepend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class AuthChatMessage {
    private String token;
    private String sender;
}
