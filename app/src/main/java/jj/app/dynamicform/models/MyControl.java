package jj.app.dynamicform.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyControl {

    @SerializedName("control_type")
    @Expose
    private String controlType;

    @SerializedName("control_id")
    @Expose
    private int controlId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("is_required")
    @Expose
    private boolean isRequired;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("options")
    @Expose
    private List<MyOptions> options = null;

    public MyControl() {
        this.controlType = "";
        this.controlId = 0;
        this.title = "";
        this.isRequired = false;
        this.value = "";
        this.options = new ArrayList<>();
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public int getControlId() {
        return controlId;
    }

    public void setControlId(int controlId) {
        this.controlId = controlId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<MyOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MyOptions> options) {
        this.options = options;
    }
}
