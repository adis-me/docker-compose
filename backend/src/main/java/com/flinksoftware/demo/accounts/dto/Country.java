package com.flinksoftware.demo.accounts.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Country {
    String id;
    String name;
}
