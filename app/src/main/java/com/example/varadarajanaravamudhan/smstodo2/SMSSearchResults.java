package com.example.varadarajanaravamudhan.smstodo2;

/**
 * Created by varadarajanaravamudhan on 14/05/16.
 */
public class SMSSearchResults {

    private String strAddr;
    private String strMsg;
    private boolean selected;

    public String getStrAddr(){
        return this.strAddr;
    }

    public void setStrAddr(String strAddr){
        this.strAddr = strAddr;
    }

    public String getStrMsg(){
        return this.strMsg;
    }

    public void setStrMsg(String strMsg){
        this.strMsg = strMsg;
    }

    public boolean isSelected(){
        return this.selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
