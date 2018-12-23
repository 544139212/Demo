package com.example.demo.importer;

import java.util.List;

public class B {
    String code;
    String platform;
    List<C> modules;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<C> getModules() {
        return modules;
    }

    public void setModules(List<C> modules) {
        this.modules = modules;
    }
}
