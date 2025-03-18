package com.flinksoftware.demo.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDProvider {

    public UUID generate() {
        return UUID.randomUUID();
    }
}
