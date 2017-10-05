package com.softneta;

public class TestDataEntry{
    private String studyUID;
    private String instanceUID;
    private String sopClass;
    private String transferSyntax;
    private String photometricIntepretation;
    private String bitsSotred;
    private String instanceDescrition;

    public TestDataEntry(String studyUID, String instanceUID, String sopClass, String transferSyntax, String photometricIntepretation, String bitsSotred, String instanceDescrition) {
        this.studyUID = studyUID;
        this.instanceUID = instanceUID;
        this.sopClass = sopClass;
        this.transferSyntax = transferSyntax;
        this.photometricIntepretation = photometricIntepretation;
        this.bitsSotred = bitsSotred;
        this.instanceDescrition = instanceDescrition;
    }

    public String getStudyUID() {
        return studyUID;
    }

    public String getSopClass() {
        return sopClass;
    }

    public String getTransferSyntax() {
        return transferSyntax;
    }

    public String getPhotometricIntepretation() {
        return photometricIntepretation;
    }

    public String getBitsSotred() {
        return bitsSotred;
    }

    public String getInstanceUID() {
        return instanceUID;
    }

    public String getInstanceDescrition() {
        return instanceDescrition;
    }
}
