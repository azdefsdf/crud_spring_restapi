package com.spring.test.model;

public class ImageProcessingResponse {

    private String jsonData;
    private String jsonFileName;

    public ImageProcessingResponse(String jsonData, String jsonFileName) {
        this.jsonData = jsonData;
        this.jsonFileName = jsonFileName;
    }

    // Getters and setters (optional)

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }
}
