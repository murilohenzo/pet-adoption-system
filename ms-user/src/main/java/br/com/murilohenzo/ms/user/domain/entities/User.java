package br.com.murilohenzo.ms.user.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "users")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "referenceId", unique = true)
    private String referenceId = UUID.randomUUID().toString();

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "createdBy")
    @CreatedBy
    private String createdBy;

    @Column(name = "modifiedBy")
    @LastModifiedBy
    private String modifiedBy;
}