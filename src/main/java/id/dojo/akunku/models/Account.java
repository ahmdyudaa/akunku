package id.dojo.akunku.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account {
    StringProperty signUrl;
    StringProperty username;
    StringProperty password;
    ArrayList<LocalDateTime> historyPasswordChange;

    public Account(){
        this(null, null, null);
    }

    public Account(String username, String password, String signUrl){
        this.signUrl = new SimpleStringProperty(signUrl);
        this.username = new SimpleStringProperty(username);;
        this.password = new SimpleStringProperty(password);;
        this.historyPasswordChange = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.historyPasswordChange.add(currentDateTime);
    }

    public String getSignUrl() {
        return signUrl.get();
    }

    public StringProperty signUrlProperty(){
        return signUrl;
    }

    public void setSignUrl(String sign_url) {
        this.signUrl.set(sign_url);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty(){
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.historyPasswordChange.add(currentDateTime);
    }

    public StringProperty passwordProperty(){
        return password;
    }

    public ArrayList<LocalDateTime> getHistoryPasswordChange() {
        return historyPasswordChange;
    }

    public void setHistoryPasswordChange(ArrayList<LocalDateTime> historyPasswordChange) {
        this.historyPasswordChange = historyPasswordChange;
    }
}

