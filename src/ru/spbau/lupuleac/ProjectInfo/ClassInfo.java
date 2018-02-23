package ru.spbau.lupuleac.ProjectInfo;

public class ClassInfo {
    private int numberOfMethods;
    private int numberOfFields;
    private int totalMethodsLength;
    private int totalFieldsNamesLength;

    void setNumberOfMethods(int numberOfMethods){
        this.numberOfMethods = numberOfMethods;
    }

    void setNumberOfFields(int numberOfFields){
        this.numberOfFields = numberOfFields;
    }

    void increaseTotalMethodsLength(int addition){
        totalMethodsLength += addition;
    }

    void increaseTotalFieldsNamesLength(int addition){
        totalFieldsNamesLength += addition;
    }

    int getNumberOfMethods(){
        return numberOfMethods;
    }

    int getNumberOfFields(){
        return numberOfFields;
    }

    int getTotalMethodsLength(){
        return totalMethodsLength;
    }

    int getTotalFieldsNamesLength(){
        return totalFieldsNamesLength;
    }

    double averageMethodLength(){
        return ((double) totalMethodsLength) / numberOfMethods;
    }

    double averageFieldsNamesLength(){
        return ((double) totalFieldsNamesLength) / numberOfFields;
    }

}
