package com.faridrjb.whattocook;

public class Food {

    public static final String KEY_NAME = "foodName";
    public static final String KEY_INITS = "initsNeeded";
    public static final String KEY_AMOUNT = "initsAmount";
    public static final String KEY_INSTR = "instruction";
    public static final String KEY_PHOTO = "photo";

    private String foodName;
    private String initsNeeded;
    private String initsAmount;
    private String instruction;
    private String photo;

    public Food() {};

    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public String getInitsNeeded() {
        return initsNeeded;
    }
    public void setInitsNeeded(String initsNeeded) {
        this.initsNeeded = initsNeeded;
    }
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getInitsAmount() {
        return initsAmount;
    }
    public void setInitsAmount(String initsAmount) {
        this.initsAmount = initsAmount;
    }

    @Override
    public String toString() {
        return foodName;
    }
}
