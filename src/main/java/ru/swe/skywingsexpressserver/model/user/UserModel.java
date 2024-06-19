package ru.swe.skywingsexpressserver.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.UserSurveyAssociation;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.utils.ValidationRegex;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Email may not be null or empty!")
    @Email(regexp = ValidationRegex.EMAIL_VALIDATION_REGEX)
    private String email;
    @NotBlank(message = "Password may not be null or empty!")
    private String password;
    @NotBlank(message = "Name may not be null or empty!")
    private String name;
    @NotBlank(message = "Surname may not be null or empty!")
    private String surname;
    @Builder.Default
    private Boolean twoFactor = false;
    @Builder.Default
    private Boolean childMode = false;
    private String childModePassword;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "t_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> role;
    @ManyToOne
    @JoinColumn(name = "benefit_id")
    private BenefitModel benefit;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "t_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<FlightModel> favoriteFlights;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSurveyAssociation> surveys;

    public UserModel(
            String email,
            String password,
            String name,
            String surname
    ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.twoFactor = false;
        this.childMode = false;
    }
}
