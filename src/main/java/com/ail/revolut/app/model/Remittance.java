package com.ail.revolut.app.model;

import com.ail.revolut.app.algorithm.Pdo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "remittances")
@Entity
@ToString
public class Remittance extends Pdo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long fromId;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long toId;

    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_from_id")
    private Account from;

    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_to_id")
    private Account to;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date performed;

    public Date getPerformed() {
        return performed != null ? (Date) performed.clone() : null;
    }

    public void setPerformed(Date performed) {
        this.performed = performed != null ? (Date) performed.clone() : null;
    }

    @Override
    public Remittance clone() {
        Remittance clone;
        try {
            clone = (Remittance) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.performed = (Date) performed.clone();
        return clone;
    }
}
