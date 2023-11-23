package br.com.pontotrilha.data.vo.v1;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.pontotrilha.model.Map;
import br.com.pontotrilha.model.User;

@JsonPropertyOrder({ "id", "locationName", "street", "neighborhood", "city",
		"state", "zipCode", "complement", "eventName", "description",
		"startDate", "endDate", "ticketTitle", "quantity", "tickePrice",
		"startOfSales", "endOfSales", "minPurchaseQuantity", "maxPurchaseQuantity", "eventStatus" })
public class EventVO extends RepresentationModel<EventVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Mapping("id")
	private Long key;

	private String locationName;

	private String street;

	private String neighborhood;

	private String city;

	private String state;

	private String zipCode;

	private String complement;

	private String eventName;

	private String description;

	private Date startDate;

	private Date endDate;

	private String ticketTitle;

	private Long quantity;

	private Double tickePrice;

	private Date startOfSales;

	private Date endOfSales;

	private Long minPurchaseQuantity;

	private Long maxPurchaseQuantity;

	private Long eventStatus;

	@JsonIgnore
	private User createdByUser;

	private Map map;

	public EventVO() {
	}

	@JsonProperty("createdByUserId")
	public Long getCreatedByUserId() {
		return (createdByUser != null) ? createdByUser.getId() : null;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getTickePrice() {
		return tickePrice;
	}

	public void setTickePrice(Double tickePrice) {
		this.tickePrice = tickePrice;
	}

	public Date getStartOfSales() {
		return startOfSales;
	}

	public void setStartOfSales(Date startOfSales) {
		this.startOfSales = startOfSales;
	}

	public Date getEndOfSales() {
		return endOfSales;
	}

	public void setEndOfSales(Date endOfSales) {
		this.endOfSales = endOfSales;
	}

	public Long getMinPurchaseQuantity() {
		return minPurchaseQuantity;
	}

	public void setMinPurchaseQuantity(Long minPurchaseQuantity) {
		this.minPurchaseQuantity = minPurchaseQuantity;
	}

	public Long getMaxPurchaseQuantity() {
		return maxPurchaseQuantity;
	}

	public void setMaxPurchaseQuantity(Long maxPurchaseQuantity) {
		this.maxPurchaseQuantity = maxPurchaseQuantity;
	}

	public Long getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Long eventStatus) {
		this.eventStatus = eventStatus;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((locationName == null) ? 0 : locationName.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		result = prime * result + ((complement == null) ? 0 : complement.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((ticketTitle == null) ? 0 : ticketTitle.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((tickePrice == null) ? 0 : tickePrice.hashCode());
		result = prime * result + ((startOfSales == null) ? 0 : startOfSales.hashCode());
		result = prime * result + ((endOfSales == null) ? 0 : endOfSales.hashCode());
		result = prime * result + ((minPurchaseQuantity == null) ? 0 : minPurchaseQuantity.hashCode());
		result = prime * result + ((maxPurchaseQuantity == null) ? 0 : maxPurchaseQuantity.hashCode());
		result = prime * result + ((eventStatus == null) ? 0 : eventStatus.hashCode());
		result = prime * result + ((createdByUser == null) ? 0 : createdByUser.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		EventVO other = (EventVO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (locationName == null) {
			if (other.locationName != null)
				return false;
		} else if (!locationName.equals(other.locationName))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (neighborhood == null) {
			if (other.neighborhood != null)
				return false;
		} else if (!neighborhood.equals(other.neighborhood))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		if (complement == null) {
			if (other.complement != null)
				return false;
		} else if (!complement.equals(other.complement))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (ticketTitle == null) {
			if (other.ticketTitle != null)
				return false;
		} else if (!ticketTitle.equals(other.ticketTitle))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (tickePrice == null) {
			if (other.tickePrice != null)
				return false;
		} else if (!tickePrice.equals(other.tickePrice))
			return false;
		if (startOfSales == null) {
			if (other.startOfSales != null)
				return false;
		} else if (!startOfSales.equals(other.startOfSales))
			return false;
		if (endOfSales == null) {
			if (other.endOfSales != null)
				return false;
		} else if (!endOfSales.equals(other.endOfSales))
			return false;
		if (minPurchaseQuantity == null) {
			if (other.minPurchaseQuantity != null)
				return false;
		} else if (!minPurchaseQuantity.equals(other.minPurchaseQuantity))
			return false;
		if (maxPurchaseQuantity == null) {
			if (other.maxPurchaseQuantity != null)
				return false;
		} else if (!maxPurchaseQuantity.equals(other.maxPurchaseQuantity))
			return false;
		if (eventStatus == null) {
			if (other.eventStatus != null)
				return false;
		} else if (!eventStatus.equals(other.eventStatus))
			return false;
		if (createdByUser == null) {
			if (other.createdByUser != null)
				return false;
		} else if (!createdByUser.equals(other.createdByUser))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}
}