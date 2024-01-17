package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProfileItem {
    String imagePath;
    String name;
    String email;
    Map<String, String> passwords = new HashMap<>();
    String phone;
    String address;
    String gender;
    String dateOfBirth;
    boolean allowNoti;
    List<String> joinedVolunteer;
    int points;

    public ProfileItem() {
        joinedVolunteer = new ArrayList<>();
    }

    public ProfileItem(String imagePath, String name, String email, String password, String phone, String address, String gender, String dateOfBirth, boolean allowNoti, int points) {
        this.imagePath = imagePath;
        this.name = name;
        this.email = email;
        this.passwords.put("plain", password);
        this.passwords.put("hashed", hashPassword(password));
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.allowNoti = allowNoti;
        this.points = points;
        this.joinedVolunteer = new ArrayList<>();
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getPasswords() {
        return passwords;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isAllowNoti() {
        return allowNoti;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public List<String> getJoinedVolunteer() {
        return joinedVolunteer;
    }

    public void joinVolunteer(UUID id) {
        joinedVolunteer.add(id.toString());
    }

    public String maskedPassword() {
        return passwordMask(this.passwords.get("plain"));
    }

    public String passwordMask(String password) {
        String mask = "";
        if (password != null) for (int i = 0; i < password.length(); i++) mask += "*";
        return mask;
    }

    public Map<String, Object> toMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String hashPassword(String passwordHash) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        messageDigest.update(passwordHash.getBytes());
        byte[] mdArray = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(v));
        }

        return stringBuilder.toString();
    }

    public static ProfileItem testData() {
        return new ProfileItem(
                "https://icon-library.com/images/admin-user-icon/admin-user-icon-4.jpg",
                "Test Admin",
                "admin@email.com",
                "admin123",
                "011-22334345",
                "UM, KL, Malaysia",
                "Male",
                "01/12/2000",
                true,
                1590
        );
    }
}
