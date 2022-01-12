package jj.app.dynamicform.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyForm {
    @SerializedName("form_data")
    @Expose
    private FormData formData;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("is_submitted")
    @Expose
    private Boolean isSubmitted;
    @SerializedName("form_link")
    @Expose
    private String formLink;

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public String getFormLink() {
        return formLink;
    }

    public void setFormLink(String formLink) {
        this.formLink = formLink;
    }

}
