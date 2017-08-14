package com.ail.revolut.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "accounts")
@Entity
public class Account extends Pdo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private BigDecimal balance;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @Getter
    @Setter
    @OneToMany(mappedBy = "from", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Remittance> remittances = new ArrayList<>();

    @Override
    public String toString() {
        return "Account(" +
                "id=" + id +
                ", balance=" + balance +
                ", owner=" + (owner != null ? owner.getId() : null) +
                ')';
    }
}
