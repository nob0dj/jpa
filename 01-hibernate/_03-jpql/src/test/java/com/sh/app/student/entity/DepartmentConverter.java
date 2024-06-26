package com.sh.app.student.entity;

import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;


public class DepartmentConverter implements AttributeConverter<Department, String> {
 
    @Override
    public String convertToDatabaseColumn(Department department) {
        if (department == null) {
            return null;
        }
        return department.getCode();
    }

    @Override
    public Department convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Department.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}