package com.example.user.domain.model;

import com.example.user.domain.SignUpForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = com.example.userapi.domain.model.BaseEntity.class) // 변경이력
public class Customer extends com.example.userapi.domain.model.BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify = false;


    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verify(false)
                .build();
    }
}
