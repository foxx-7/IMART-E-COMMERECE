package com.imart.order.dto.local;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckOutSession {
    private Long userId;
    private String sessionId;
    private boolean isActive;
    private boolean isEnded;
    private LocalDateTime creationTimeStamp;
}
