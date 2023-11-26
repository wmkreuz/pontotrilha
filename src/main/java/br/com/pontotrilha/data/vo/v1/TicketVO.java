package br.com.pontotrilha.data.vo.v1;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

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

	private Event eventId;

	private User purchasedByUserId;

	public TicketVO() {
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Event getEventId() {
		return eventId;
	}

	public void setEventId(Event eventId) {
		this.eventId = eventId;
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
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((purchasedByUserId == null) ? 0 : purchasedByUserId.hashCode());
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
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (purchasedByUserId == null) {
			if (other.purchasedByUserId != null)
				return false;
		} else if (!purchasedByUserId.equals(other.purchasedByUserId))
			return false;
		return true;
	}

	
}