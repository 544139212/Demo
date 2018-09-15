package com.example.demo.enums;

public enum PlanTypeEnum {
    REN_ZHAO_CHE((byte)0, "人找车"),
    CHE_ZHAO_REN((byte)1, "车找人");

    private byte type;
    private String name;

    PlanTypeEnum(byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static boolean validate(Byte type) {
        for (PlanTypeEnum planTypeEnum : PlanTypeEnum.values()) {
            if (planTypeEnum.getType() == type.byteValue()) {
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
