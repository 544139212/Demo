package com.example.demo.enums;

public enum StoreLockStatusEnum {
    NORMAL((byte)0, "正常"),
    LOCK((byte)1, "锁定");

    private byte code;
    private String name;

    StoreLockStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (StoreLockStatusEnum current : StoreLockStatusEnum.values()) {
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
