package com.basetechz.quizo;

public class CategoryPartModel {
    private String catPartName,catPartNum,categoryPartId,categoryPartImage;

   public CategoryPartModel(){

   }
    CategoryPartModel(String catPartName, String catPartNum,String categoryPartId,String categoryPartImage){
        this.catPartName = catPartName;
        this.catPartNum= catPartNum;
        this.categoryPartId = categoryPartId;
        this.categoryPartImage=categoryPartImage;
    }

    public String getCatPartName() {
        return catPartName;
    }

    public void setCatPartName(String catPartName) {
        this.catPartName = catPartName;
    }

    public String getCatPartNum() {
        return catPartNum;
    }

    public void setCatPartNum(String catPartNum) {
        this.catPartNum = catPartNum;
    }

    public String getCategoryPartId() {
        return categoryPartId;
    }

    public void setCategoryPartId(String categoryPartId) {
        this.categoryPartId = categoryPartId;
    }

    public String getCategoryPartImage() {
        return categoryPartImage;
    }

    public void setCategoryPartImage(String categoryPartImage) {
        this.categoryPartImage = categoryPartImage;
    }
}
