package me.keano.factions.factions.player;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Member {

    private UUID uuid;
    private Role role;
    private String asterisk;

    public Member(UUID uuid, Role role) {
        this.uuid = uuid;
        this.role = role;
        this.asterisk = (role.equals(Role.MEMBER) ? "" : role.equals(Role.OFFICER) ? "*" : role.equals(Role.CAPTAIN) ? "**" : "***");
    }
}