package com.crespoter.healthangel;


import android.graphics.Bitmap;

public class Patient {
    String name;
    String age;
    String sex;
    String temperature;
    String pulse;
    String id;
    public Patient(String name, String age, String sex, String temperature, String pulse, String id)
    {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.temperature = temperature;
        this.pulse = pulse;
        this.id = id;
    }
}
