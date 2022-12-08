package com.karan.expenses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Record {
    private final float value;
    private final String category;
    private final String description;
    private final String type;
    private final float PID;

    public Record(float value, String category, String description, String type, float PID) {
        this.value = value;
        this.category = category;
        this.description = description;
        this.type = type;
        this.PID = PID;
    }

    public float getValue() {
        return value;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public float getPID() {
        return PID;
    }
}
