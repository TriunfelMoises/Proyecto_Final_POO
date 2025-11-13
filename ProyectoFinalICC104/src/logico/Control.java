package logico;

import java.io.Serializable;
import java.util.ArrayList;


public class Control implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<User> misUsers;
    private static Control control;
    private static User loginUser;

    private Control() {
        misUsers = new ArrayList<>();
    }

    public static Control getInstance() {
        if (control == null) {
            control = new Control();
        }
        return control;
    }

    public static void setControl(Control control) {
        Control.control = control;
    }

    public static User getLoginUser() {
        return loginUser;
    }

    public void regUser(User user) {
        if (user != null) {
            misUsers.add(user);
        }
    }

    public boolean confirmLogin(String text, String text2) {
        boolean login = false;
        for (User user : misUsers) {
            if (user.getUserName().equals(text) && user.getPass().equals(text2)) {
                loginUser = user;
                login = true;
            }
        }
        return login;
    }

    public ArrayList<User> getMisUsers() { return misUsers; }
    public void setMisUsers(ArrayList<User> misUsers) { this.misUsers = misUsers; }
}
///-