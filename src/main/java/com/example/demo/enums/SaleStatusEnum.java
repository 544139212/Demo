package com.example.demo.enums;

public enum SaleStatusEnum {
    STOP_SALE((byte)0, "停售"),
    ON_SALE((byte)1, "在售");

    private byte code;
    private String name;

    SaleStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (SaleStatusEnum current : SaleStatusEnum.values()) {
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
