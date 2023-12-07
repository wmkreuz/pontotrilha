package br.com.pontotrilha.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "event", referencedColumnName = "id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "purchased_by_user", referencedColumnName = "id")
	private User purchasedByUser;

	@Column(name = "url_payment", nullable = false, length = 255)
	private String urlPayment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getPurchasedByUser() {
		return purchasedByUser;
	}

	public void setPurchasedByUser(User purchasedByUser) {
		this.purchasedByUser = purchasedByUser;
	}

	public String getUrlPayment() {
		return urlPayment;
	}

	public void setUrlPayment(String urlPayment) {
		this.urlPayment = urlPayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((purchasedByUser == null) ? 0 : purchasedByUser.hashCode());
		result = prime * result + ((urlPayment == null) ? 0 : urlPayment.hashCode());
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
		Ticket other = (Ticket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (purchasedByUser == null) {
			if (other.purchasedByUser != null)
				return false;
		} else if (!purchasedByUser.equals(other.purchasedByUser))
			return false;
		if (urlPayment == null) {
			if (other.urlPayment != null)
				return false;
		} else if (!urlPayment.equals(other.urlPayment))
			return false;
		return true;
	}
}