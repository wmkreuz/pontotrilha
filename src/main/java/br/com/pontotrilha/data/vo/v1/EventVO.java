package br.com.pontotrilha.data.vo.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.pontotrilha.model.Map;
import br.com.pontotrilha.model.User;

@JsonPropertyOrder({ "id", "locationName", "street", "neighborhood", "number", "city",
		"state", "zipCode", "complement", "eventName", "description",
		"startDate", "endDate", "ticketTitle", "quantity", "tickePrice", "tickePriceStripe",
		"startOfSales", "endOfSales", "minPurchaseQuantity", "maxPurchaseQuantity", "eventStatus" })
public class EventVO extends RepresentationModel<EventVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Mapping("id")
	private Long key;

	private String locationName;

	private String street;

	private String neighborhood;

	private String number;

	private String city;

	private String state;

	private String zipCode;

	private String complement;

	private String eventName;

	private String description;

	private LocalDate startDate;

	private LocalTime startDateTime;

	private LocalDate endDate;

	private LocalTime endDateTime;

	private String ticketTitle;

	private Long quantity;

	private Double tickePrice;

	private String tickePriceStripe;

	private LocalDate startOfSales;

	private LocalTime startOfSalesTime;

	private LocalDate endOfSales;

	private LocalTime endOfSalesTime;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTickePriceStripe() {
		return tickePriceStripe;
	}

	public void setTickePriceStripe(String tickePriceStripe) {
		this.tickePriceStripe = tickePriceStripe;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public LocalDate getStartOfSales() {
		return startOfSales;
	}

	public void setStartOfSales(LocalDate startOfSales) {
		this.startOfSales = startOfSales;
	}

	public LocalTime getStartOfSalesTime() {
		return startOfSalesTime;
	}

	public void setStartOfSalesTime(LocalTime startOfSalesTime) {
		this.startOfSalesTime = startOfSalesTime;
	}

	public LocalDate getEndOfSales() {
		return endOfSales;
	}

	public void setEndOfSales(LocalDate endOfSales) {
		this.endOfSales = endOfSales;
	}

	public LocalTime getEndOfSalesTime() {
		return endOfSalesTime;
	}

	public void setEndOfSalesTime(LocalTime endOfSalesTime) {
		this.endOfSalesTime = endOfSalesTime;
	}

}