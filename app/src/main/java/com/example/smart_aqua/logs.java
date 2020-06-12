package com.example.smart_aqua;

public class logs {
    private boolean led;
    private int light,temp;
    String time;

    public logs() {
    }

    public logs(boolean led, int light, int temp, String time) {
        this.led = led;
        this.light = light;
        this.temp = temp;
        this.time = time;
    }

    public boolean isLed() {
        return led;
    }

    public void setLed(boolean led) {
        this.led = led;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
