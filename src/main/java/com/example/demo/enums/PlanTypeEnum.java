package com.example.demo.enums;

public enum PlanTypeEnum {
    REN_ZHAO_CHE((byte)0, "人找车"),
    CHE_ZHAO_REN((byte)1, "车找人");

    private byte code;
    private String name;

    PlanTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (PlanTypeEnum current : PlanTypeEnum.values()) {
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
