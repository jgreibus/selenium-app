package com.softneta;

public class Study {
    private String uid;
    private String sopClass;
    private String transferSyntax;
    private String photometric;
    private String bitStored;

    public Study(String uid, String sopClass, String transferSyntax, String photometric, String bitStored) {
        this.uid = uid;
        this.sopClass = sopClass;
        this.transferSyntax = transferSyntax;
        this.photometric = photometric;
        this.bitStored = bitStored;
    }

    public String getUid() {
        return uid;
    }

    public String getSopClass() {
        return sopClass;
    }

    public String getTransferSyntax() {
        return transferSyntax;
    }

    public String getPhotometric() {
        return photometric;
    }

    public String getBitStored() {
        return bitStored;
    }
}
