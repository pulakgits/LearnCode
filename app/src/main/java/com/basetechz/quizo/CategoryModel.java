package com.basetechz.quizo;

public class CategoryModel {
    private String categoryImage;
    private String categoryId,categoryName;

    public CategoryModel(){

    }

    public CategoryModel(String categoryId, String categoryImage, String categoryName){
        this.categoryId = categoryId;
        this.categoryImage=categoryImage;
        this.categoryName=categoryName;
    }


    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
