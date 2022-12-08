package com.karan.expenses;

public class SingleRecord {
    private Float value;
    private String Category,Discription,TYPE;
    private float PID;
    public SingleRecord(Float value, String category, String discription,String TYPE,float pid) {
        this.value = value;
        this.Category = category;
        this.Discription = discription;
        this.TYPE  = TYPE;
        this.PID = pid;
    }


    public float getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
