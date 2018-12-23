package com.example.demo.enums;

public enum StoreOpenStatusEnum {
    CLOSE((byte)0, "休息中"),
    OPEN((byte)1, "正在营业");

    private byte code;
    private String name;

    StoreOpenStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (StoreOpenStatusEnum current : StoreOpenStatusEnum.values()) {
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
