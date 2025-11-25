package com.sach.fitnesstracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_setting")
public class SystemSetting {

    @Id
    @Column(name = "key_name")
    private String keyName;

    @Column(name = "setting_value")
    private String value;

    public SystemSetting() {
    }

    public SystemSetting(String keyName, String value) {
        this.keyName = keyName;
        this.value = value;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


