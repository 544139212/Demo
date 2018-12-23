package com.example.demo.enums;

public enum AuditStatusEnum {
    REJECT((byte)-1, "审核未通过"),
    PENDING((byte)-1, "待审核"),
    APPROVAL((byte)1, "审核通过");

    private byte code;
    private String name;

    AuditStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean validate(Byte code) {
        for (AuditStatusEnum current : AuditStatusEnum.values()) {
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
