package ecutb.fishingtrip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class FishermanRole {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name =  "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String roleId;
    @Column(unique = true)
    private String role;

    public FishermanRole() {}

    public FishermanRole(String role){
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishermanRole that = (FishermanRole) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role);
    }

    @Override
    public String
    toString() {
        return "FishermanRole{" +
                "roleId='" + roleId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
