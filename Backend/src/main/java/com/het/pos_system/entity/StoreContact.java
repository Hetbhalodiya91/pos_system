package com.het.pos_system.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class StoreContact {

    //TODO [Reverse Engineering] generate columns from DB
    @Pattern(regexp = "^\\+?[0-9. ()-]{10,12}$", message = "Phone number is invalid")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;
    
    private String address;

}