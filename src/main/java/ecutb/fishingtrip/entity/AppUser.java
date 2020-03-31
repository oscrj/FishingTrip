package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String userId;
    @Column(unique = true, length = 60)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate regDate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "appUser_id_appUserRole_id",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "appUserRole_id"))
    private Set<AppUserRole> appUserRoles;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "appUser")
    private List<FishingTrip> fishingTripList;

    public AppUser(){}

    public AppUser(String userName, String email, String password, LocalDate regDate) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Set<AppUserRole> getAppUserRoles() {
        return appUserRoles;
    }

    public void setAppUserRoles(Set<AppUserRole> appUserRoles) {
        this.appUserRoles = appUserRoles;
    }

    public List<FishingTrip> getFishingTripList() {
        return fishingTripList;
    }

    public void setFishingTripList(List<FishingTrip> fishingTripList) {
        if(this.fishingTripList == null){
            fishingTripList = new ArrayList<>();
        }
        this.fishingTripList = fishingTripList;
    }

    public boolean addFishingTrip(FishingTrip fishingTrip){
        if(this.fishingTripList == null) fishingTripList = new ArrayList<>();
        if(fishingTrip == null) return false;
        if(fishingTripList.contains(fishingTrip)) return false;

        fishingTripList.add(fishingTrip);
        fishingTrip.setAppUser(this);
        return true;
    }

    public boolean removeFishingTrip(FishingTrip fishingTrip){
        if(this.fishingTripList == null) fishingTripList = new ArrayList<>();
        if(fishingTrip == null) return false;

        fishingTripList.remove(fishingTrip);
        fishingTrip.setAppUser(null);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(userId, appUser.userId) &&
                Objects.equals(userName, appUser.userName) &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(regDate, appUser.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email, regDate);
    }

    @Override
    public String toString() {
        return "Fisherman{" +
                "fishermanId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
