package lk.earth.earthuniversity.entity;

public class GlobalSetting {

    private static String userName = "AdminEUC";

    public static void setUser(User1 user) {
        userName = user.getUsername();
    }

    public static String getUserName() {
        return userName;
    }
}

