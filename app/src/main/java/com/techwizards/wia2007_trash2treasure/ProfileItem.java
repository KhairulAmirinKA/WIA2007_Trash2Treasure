package com.techwizards.wia2007_trash2treasure;

import java.util.HashMap;
import java.util.Map;

public class ProfileItem {
    String imagePath;
    String name;
    String email;
    String password;
    String phone;
    String address;
    String gender;
    String dateOfBirth;
    boolean allowNoti;

    public ProfileItem() {
    }

    public ProfileItem(String imagePath, String name, String email, String password, String phone, String address, String gender, String dateOfBirth, boolean allowNoti) {
        this.imagePath = imagePath;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.allowNoti = allowNoti;
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

    public String getPassword() {
        return password;
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

    public String passwordMask() {
        String mask = "";
        if (password != null) for (int i = 0; i < password.length(); i++) mask += "*";
        return mask;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("password", password);
        map.put("phone", phone);
        map.put("address", address);
        map.put("gender", gender);
        map.put("dateOfBirth", dateOfBirth);
        map.put("allowNotifications", allowNoti);
        return map;
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
                true
        );
    }
}
