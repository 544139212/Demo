package com.example.demo.enums;

public enum AppointTypeEnum {
    NOW((byte)0, "现在"),
    OTHER((byte)1, "预设");

    private byte type;
    private String name;

    AppointTypeEnum(byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static boolean validate(Byte type) {
        for (AppointTypeEnum current : AppointTypeEnum.values()) {
            if (current.getType() == type.byteValue()) {
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
