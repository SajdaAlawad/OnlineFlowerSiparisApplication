package com.example.onlinesiparisapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OnlineSiparisModel implements Parcelable {

    private String name;
    private String image;
    private float delivery_charge;

    private List<Menu> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


   public float getDelivery_charge() {
      return delivery_charge;
   }
   public void setDelivery_charge(float delivery_charge) {
       this.delivery_charge = delivery_charge;
    }


    public List<Menu> getMenus() {
        return menus;
    }


    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    protected OnlineSiparisModel(Parcel in) {
        name = in.readString();
        image = in.readString();
        delivery_charge = in.readFloat();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<OnlineSiparisModel> CREATOR = new Creator<OnlineSiparisModel>() {
        @Override
        public OnlineSiparisModel createFromParcel(Parcel in) {
            return new OnlineSiparisModel(in);
        }

        @Override
        public OnlineSiparisModel[] newArray(int size) {
            return new OnlineSiparisModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeFloat(delivery_charge);
        parcel.writeTypedList(menus);
    }
}
