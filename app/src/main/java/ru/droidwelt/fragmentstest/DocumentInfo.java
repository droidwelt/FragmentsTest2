package ru.droidwelt.fragmentstest;


import android.os.Parcel;
import android.os.Parcelable;

public class DocumentInfo implements Parcelable {
    private String ms1;
    private String ms2;

    public DocumentInfo(String s1, String s2) {
        ms1 = s1;
        ms2 = s2;
    }

    public DocumentInfo(Parcel in) {
     /*   String[] data = new String[2];
        in.readStringArray(data);
        ms1 = data[0];
        ms2 = data[1]; */
        this.ms1 = in.readString();
        this.ms2 = in.readString();
    }

    public void setms1(String s1) {
        ms1 = s1;
    }

    public String getms1() {
        return ms1;
    }

    public void setms2(String s2) {
        ms2 = s2;
    }

    public String getms2() {
        return ms2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      //  dest.writeStringArray(new String[] { ms1, ms2 });
        dest.writeString (this.ms1);
        dest.writeString (this.ms2);
    }

    public static final Parcelable.Creator<DocumentInfo> CREATOR = new Parcelable.Creator<DocumentInfo>() {

        @Override
        public DocumentInfo createFromParcel(Parcel source) {
            return new DocumentInfo(source);
        }

        @Override
        public DocumentInfo[] newArray(int size) {
            return new DocumentInfo[size];
        }
            };

    @Override
    public String toString() {
        return "DocumentInfo{" +
                "ms1='" + ms1 + '\'' +
                ", ms2='" + ms2 + '\'' +
                '}';
    }
}
