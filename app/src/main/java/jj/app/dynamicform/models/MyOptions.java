package jj.app.dynamicform.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOptions {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("value")
    @Expose
    private String value;

    public MyOptions(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
