package entity;


import javax.annotation.Nullable;

public class User {
    private String name;
    private String email;
    private String uid;
    private boolean img1;
    private boolean img2;
    private boolean img3;
    private boolean img4;
    private boolean img5;
    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(@Nullable String name, @Nullable String email, @Nullable String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.img1 = false;
        this.img2 = false;
        this.img3 = false;
        this.img4 = false;
        this.img5 = false;
    }

    public boolean isImg1() {
        return img1;
    }

    public boolean isImg2() {
        return img2;
    }
    public boolean isImg3() {
        return img3;
    }

    public boolean isImg4() {
        return img4;
    }
    public boolean isImg5() {
        return img5;
    }


}
