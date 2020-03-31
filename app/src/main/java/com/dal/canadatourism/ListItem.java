package com.dal.canadatourism;

public class ListItem {
    private String head;
    private String desc;
    private String imageurl;
    private String cityId;

    public ListItem( String head, String desc, String imageurl) {
        this.head = head;
        this.desc = desc;
        this.imageurl = imageurl;
       // this.cityId = cityId;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageurl() {
        return imageurl;
    }


}
