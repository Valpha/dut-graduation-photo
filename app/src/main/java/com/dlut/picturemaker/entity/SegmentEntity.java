package com.dlut.picturemaker.entity;

public class SegmentEntity {
    private String foreground;
    private String scoremap;
    private String labelmap;
    private String logId;

    public String getForeground() {
        return foreground;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
    }

    public String getScoremap() {
        return scoremap;
    }

    public void setScoremap(String scoremap) {
        this.scoremap = scoremap;
    }

    public String getLabelmap() {
        return labelmap;
    }

    public void setLabelmap(String labelmap) {
        this.labelmap = labelmap;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
