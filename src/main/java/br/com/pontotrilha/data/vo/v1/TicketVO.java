package br.com.pontotrilha.data.vo.v1;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.pontotrilha.model.Event;
import br.com.pontotrilha.model.User;

@JsonPropertyOrder({ "id",  "eventId", "purchasedByUserId" })
public class TicketVO extends RepresentationModel<TicketVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Mapping("id")
	private Long key;

	@JsonIgnore
	private Event event;

	@JsonIgnore
	private User purchasedByUser;

	public TicketVO() {
	}

	@JsonProperty("purchasedByUserId")
	public Long getCreatedByUserId() {
		return (purchasedByUser != null) ? purchasedByUser.getId() : null;
	}

	@JsonProperty("eventId")
	public Long getEventId() {
		return (event != null) ? event.getId() : null;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((purchasedByUser == null) ? 0 : purchasedByUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketVO other = (TicketVO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
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
		return true;
	}

		
}