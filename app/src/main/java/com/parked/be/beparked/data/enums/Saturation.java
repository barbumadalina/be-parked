package com.parked.be.beparked.data.enums;

import java.util.Random;

public enum Saturation {
    LOW ("LOW"),
    MEDIUM ("MEDIUM"),
    HIGH ("HIGH"),
    UNKNOWN("UNKNOWN");

    private String saturation;

    Saturation(String saturation) {
        this.saturation = saturation;
    }

    /**
     * todo: remove this method
     * @return random Saturation
     */
    public static Saturation getRandomSaturation(){
        Random random = new Random();

        switch (random.nextInt(3)){
            case 0 : return LOW;
            case 1 : return MEDIUM;
            case 2 : return HIGH;
            default: return LOW;
        }
    }

    @Override
    public String toString() {
        return this.saturation;
    }

    public int toColor() {
        switch (this.saturation){
            case "LOW": return android.R.color.holo_green_light;
            case "MEDIUM": return android.R.color.holo_orange_dark;
            case "HIGH": return android.R.color.holo_red_light;
            case "UNKNOWN": return android.R.color.holo_blue_dark;
            default: return 0;
        }
    }
}
