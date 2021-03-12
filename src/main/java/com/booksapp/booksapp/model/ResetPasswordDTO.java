package com.booksapp.booksapp.model;

public class ResetPasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmedNewPassword;

    public ResetPasswordDTO(String currentPassword, String newPassword, String confirmedNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
