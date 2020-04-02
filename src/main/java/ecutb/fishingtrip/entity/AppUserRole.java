package ecutb.fishingtrip.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AppUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column(unique = true)
    private UserRole role;

    public AppUserRole() {}

    public AppUserRole(UserRole role){
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserRole that = (AppUserRole) o;
        return roleId == that.roleId &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role);
    }

    @Override
    public String toString() {
        return "AppUserRole{" +
                "roleId=" + roleId +
                ", role=" + role +
                '}';
    }
}
