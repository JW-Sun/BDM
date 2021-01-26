package com.jw.bigwhalemonitor.entity;


import lombok.Data;


public class CmdRecordWithBLOBs extends CmdRecord {
    private String content;

    private String outputs;

    private String errors;

    public CmdRecordWithBLOBs() {
        super();
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs == null ? null : outputs.trim();
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors == null ? null : errors.trim();
    }
}