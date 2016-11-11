package com.grupio.notes;

public class NotesData {

    private long id = 0;
    private String noteId = "";
    private String noteType = "";
    private String noteText = "";
    private String noteDate = "";
    private String noteReminder = "";
    private String lastOperation = "";
    private String noteSync = "";

    private boolean isSelected = false;

    public NotesData() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteReminder() {
        return noteReminder;
    }

    public void setNoteReminder(String noteReminder) {
        this.noteReminder = noteReminder;
    }

    public String getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(String lastOperation) {
        this.lastOperation = lastOperation;
    }

    public String getNoteSync() {
        return noteSync;
    }

    public void setNoteSync(String noteSync) {
        this.noteSync = noteSync;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

}
