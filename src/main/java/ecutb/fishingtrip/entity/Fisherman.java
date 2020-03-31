package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Fisherman {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String fishermanId;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate regDate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "fisherman_id_fisherman_role",
            joinColumns = @JoinColumn(name = "fisherman_id"),
            inverseJoinColumns = @JoinColumn(name = "fishermanRole_id"))
    private Set<FishermanRole> fishermanRoles;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "fisherman")
    private List<FishingTrip> fishingTripList;

    public Fisherman(){}

    public Fisherman(String userName, String email, String password, LocalDate regDate) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
    }

    public String getFishermanId() {
        return fishermanId;
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

    public Set<FishermanRole> getFishermanRoles() {
        return fishermanRoles;
    }

    public void setFishermanRoles(Set<FishermanRole> fishermanRoles) {
        this.fishermanRoles = fishermanRoles;
    }

    public List<FishingTrip> getFishingTripList() {
        return fishingTripList;
    }

    public void setFishingTripList(List<FishingTrip> fishingTripList) {
        if(this.fishingTripList.isEmpty()) fishingTripList = new ArrayList<>();

        if(fishingTripList != null){
            fishingTripList.forEach(this::addFishingTrip);
        }else{
            fishingTripList.forEach(fishingTrip -> fishingTrip.setFisherman(null));
        }
        this.fishingTripList = fishingTripList;
    }

    public boolean addFishingTrip(FishingTrip fishingTrip){
        if(this.fishingTripList == null) fishingTripList = new ArrayList<>();
        if(fishingTrip == null) return false;
        if(fishingTripList.contains(fishingTrip)) return false;

        fishingTripList.add(fishingTrip);
        fishingTrip.setFisherman(this);
        return true;
    }

    public boolean removeFishingTrip(FishingTrip fishingTrip){

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fisherman fisherman = (Fisherman) o;
        return Objects.equals(fishermanId, fisherman.fishermanId) &&
                Objects.equals(userName, fisherman.userName) &&
                Objects.equals(email, fisherman.email) &&
                Objects.equals(regDate, fisherman.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishermanId, userName, email, regDate);
    }

    @Override
    public String toString() {
        return "Fisherman{" +
                "fishermanId='" + fishermanId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
