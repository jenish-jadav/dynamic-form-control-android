package jj.app.dynamicform.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FormData {

    @SerializedName("childSteps")
    @Expose
    private List<Object> childSteps = null;
    @SerializedName("schemas")
    @Expose
    private List<Schema> schemas = null;

    public List<Object> getChildSteps() {
        return childSteps;
    }

    public void setChildSteps(List<Object> childSteps) {
        this.childSteps = childSteps;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

}
