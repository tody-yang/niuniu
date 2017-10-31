package cn.com.niuniu.yj.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * <h1>User相关bean类</h1>
 * <p>详细功能说明
 *
 * @author: niuniu
 * @date: 2017/10/10.
 */
@Entity  //GreenDao实体注解标志
public class User implements Parcelable {

    @Id  //数据库ID的注解标志，改标注修饰的对象只能是Long型
    private Long id;

    @Property(nameInDb = "USER_NAME")  //该注解的意思是作为数据库一属性定义，名称为USER_NAME
    private String userName;

    @Property(nameInDb = "USER_AGE")
    private int userAge;

    @Property(nameInDb = "DESC")
    private String userDesc;

    @Property(nameInDb = "PHONE")
    private String phone;//添加的属性，之所以用String。是因为greendao中int不能为null，很坑！

    protected User(Parcel in) {
        userName = in.readString();
        userAge = in.readInt();
        userDesc = in.readString();
        phone = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserDesc() {
        return this.userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public int getUserAge() {
        return this.userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 60586785)
    public User(Long id, String userName, int userAge, String userDesc,
            String phone) {
        this.id = id;
        this.userName = userName;
        this.userAge = userAge;
        this.userDesc = userDesc;
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeInt(userAge);
        parcel.writeString(userDesc);
        parcel.writeString(phone);
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userDesc='" + userDesc + '\'' +
                ", phone=" + phone +
                '}';
    }
}
