package com.example.bookstoreapp.enums;

import lombok.Getter;

import javax.management.relation.Role;

@Getter
public enum RoleEnum {
    AUTHOR(1),STUDENT(2);
    RoleEnum(int value){
        this.value=value;
    }
    final int value;

}
