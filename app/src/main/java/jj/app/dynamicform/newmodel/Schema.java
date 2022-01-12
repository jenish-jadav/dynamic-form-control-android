package jj.app.dynamicform.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schema {
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("ngModel")
    @Expose
    private String ngModel;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("other")
    @Expose
    private Boolean other;
    @SerializedName("userData")
    @Expose
    private List<String> userData = null;
    @SerializedName("inline")
    @Expose
    private Boolean inline;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    @SerializedName("isShow")
    @Expose
    private Boolean isShow;

    public List<String> getUserData() {
        return userData;
    }

    public void setUserData(List<String> userData) {
        this.userData = userData;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getNgModel() {
        return ngModel;
    }

    public void setNgModel(String ngModel) {
        this.ngModel = ngModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getOther() {
        return other;
    }

    public void setOther(Boolean other) {
        this.other = other;
    }

    public Boolean getInline() {
        return inline;
    }

    public void setInline(Boolean inline) {
        this.inline = inline;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
