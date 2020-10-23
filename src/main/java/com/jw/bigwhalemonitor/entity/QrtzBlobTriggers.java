package com.jw.bigwhalemonitor.entity;

public class QrtzBlobTriggers extends QrtzBlobTriggersKey {
    private byte[] blobData;

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }
}