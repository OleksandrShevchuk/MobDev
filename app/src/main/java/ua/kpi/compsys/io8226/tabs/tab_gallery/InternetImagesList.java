package ua.kpi.compsys.io8226.tabs.tab_gallery;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InternetImagesList {

    @SerializedName("hits")
    private List<InternetImage> internetImages = new ArrayList<>();


    public InternetImagesList(List<InternetImage> internetImages) {
        this.internetImages = internetImages;
    }

    public InternetImagesList() {
    }

    public List<InternetImage> getInternetImages() {
        return internetImages;
    }

    public void setInternetImages(List<InternetImage> internetImages) {
        this.internetImages = internetImages;
    }

    public InternetImage getInternetImage(int index) {
        return internetImages.get(index);
    }

    public void setInternetImage(int index, InternetImage internetImage) {
        internetImages.set(index, internetImage);
    }

    public void addInternetImage(InternetImage internetImage) {
        internetImages.add(internetImage);
    }
}
