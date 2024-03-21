package com.linger.example.common.model;

import java.io.Serializable;

/**
 * @author linger
 * @date 2024/3/21 16:44
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
