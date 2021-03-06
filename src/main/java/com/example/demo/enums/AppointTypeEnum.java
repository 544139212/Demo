package com.example.demo.enums;

public enum AppointTypeEnum {
    NOW((byte)0, "现在"),
    OTHER((byte)1, "预设");

    private byte code;
    private String name;

    AppointTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (AppointTypeEnum current : AppointTypeEnum.values()) {
            if (current.getCode() == code.byteValue()) {
                return true;
            }
        }
        return false;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
