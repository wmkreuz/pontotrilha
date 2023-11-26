package br.com.pontotrilha.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payments"
)
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_paid")
    private Long amountPaid;

    private String email;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "stripe_customer_id")
    private String customerId;

    @ManyToOne
	@JoinColumn(name = "purchased_by_user_id", referencedColumnName = "id")
	private User purchasedByUserId;

    public Payments() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public User getPurchasedByUserId() {
        return purchasedByUserId;
    }

    public void setPurchasedByUserId(User purchasedByUserId) {
        this.purchasedByUserId = purchasedByUserId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((amountPaid == null) ? 0 : amountPaid.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((cardName == null) ? 0 : cardName.hashCode());
        result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((purchasedByUserId == null) ? 0 : purchasedByUserId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payments other = (Payments) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (amountPaid == null) {
            if (other.amountPaid != null)
                return false;
        } else if (!amountPaid.equals(other.amountPaid))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (cardName == null) {
            if (other.cardName != null)
                return false;
        } else if (!cardName.equals(other.cardName))
            return false;
        if (paymentDate == null) {
            if (other.paymentDate != null)
                return false;
        } else if (!paymentDate.equals(other.paymentDate))
            return false;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
            return false;
        if (purchasedByUserId == null) {
            if (other.purchasedByUserId != null)
                return false;
        } else if (!purchasedByUserId.equals(other.purchasedByUserId))
            return false;
        return true;
    }

    
}