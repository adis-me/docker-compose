package com.flinksoftware.demo.security.dto;

import lombok.Builder;

@Builder
public record AuthUser(String name, String email) {
}
