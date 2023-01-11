package io.codelex.scheduler;

import java.time.LocalTime;

public record Schedule(LocalTime time, int bitmask) {
}
