package nextstep.entity;

import java.util.Arrays;

public enum MemberRole {
    ADMIN(0), USER(1);

    private int value;

    MemberRole(int value) {
        this.value = value;
    }

    public static boolean isAdmin(MemberRole roll){
        return roll == ADMIN;
    }

    public int getValue() {
        return value;
    }
    public static  MemberRole findRole(int x){
        return Arrays.stream(MemberRole.values()).filter(role -> role.getValue() == x).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 역할 입니다."));

    }
}
