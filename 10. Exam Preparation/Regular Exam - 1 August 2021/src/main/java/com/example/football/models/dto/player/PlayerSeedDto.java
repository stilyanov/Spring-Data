package com.example.football.models.dto.player;

import com.example.football.models.dto.stat.StatIdDto;
import com.example.football.models.dto.team.TeamNameDto;
import com.example.football.models.dto.town.TownNameDto;
import com.example.football.models.entity.PositionType;
import com.example.football.util.LocalDateAdapter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto implements Serializable {

    @XmlElement(name = "first-name")
    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement
    @NotNull
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @NotNull
    private LocalDate birthDate;

    @XmlElement
    @NotNull
    private PositionType position;

    @XmlElement(name = "town")
    private TownNameDto townNameDto;

    @XmlElement(name = "team")
    private TeamNameDto teamNameDto;

    @XmlElement(name = "stat")
    private StatIdDto statIdDto;

    public TeamNameDto getTeamNameDto() {
        return teamNameDto;
    }

    public void setTeamNameDto(TeamNameDto teamNameDto) {
        this.teamNameDto = teamNameDto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PositionType getPosition() {
        return position;
    }

    public void setPosition(PositionType position) {
        this.position = position;
    }

    public TownNameDto getTownNameDto() {
        return townNameDto;
    }

    public void setTownNameDto(TownNameDto townNameDto) {
        this.townNameDto = townNameDto;
    }

    public StatIdDto getStatIdDto() {
        return statIdDto;
    }

    public void setStatIdDto(StatIdDto statIdDto) {
        this.statIdDto = statIdDto;
    }
}
