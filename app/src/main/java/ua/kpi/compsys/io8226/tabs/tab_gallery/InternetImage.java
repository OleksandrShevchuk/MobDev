package ua.kpi.compsys.io8226.tabs.tab_gallery;

import com.google.gson.annotations.SerializedName;

public class InternetImage {

    @SerializedName("webformatURL")
    String webFormatUrl;


    public InternetImage(String webFormatUrl) {
        this.webFormatUrl = webFormatUrl;
    }


    public String getWebFormatUrl() {
        return webFormatUrl;
    }

    public void setWebFormatUrl(String webFormatUrl) {
        this.webFormatUrl = webFormatUrl;
    }
}
