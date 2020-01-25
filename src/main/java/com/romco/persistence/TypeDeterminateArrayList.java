package com.romco.persistence;

import java.util.ArrayList;

public class TypeDeterminateArrayList<T> extends ArrayList {
    private Class<?> type;

    public TypeDeterminateArrayList(Object type) {
        this.type = type.getClass();
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
