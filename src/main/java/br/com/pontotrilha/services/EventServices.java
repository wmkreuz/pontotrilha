package br.com.pontotrilha.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.controllers.EventController;
import br.com.pontotrilha.data.vo.v1.EventVO;
import br.com.pontotrilha.exceptions.RequiredObjectIsNullException;
import br.com.pontotrilha.exceptions.ResourceNotFoundException;
import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.model.Event;
import br.com.pontotrilha.model.User;
import br.com.pontotrilha.repositories.EventRepository;

@Service
public class EventServices {

	private Logger logger = Logger.getLogger(EventServices.class.getName());

	@Autowired
	EventRepository repository;

	public List<EventVO> findAll() {

		logger.info("Finding all events!");

		var events = DozerMapper.parseListObjects(repository.findAll(), EventVO.class);
		events
				.stream()
				.forEach(p -> p.add(linkTo(methodOn(EventController.class).findById(p.getKey())).withSelfRel()));
		return events;
	}

	public EventVO findById(Long id) {

		logger.info("Finding one event!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, EventVO.class);
		vo.add(linkTo(methodOn(EventController.class).findById(id)).withSelfRel());
		return vo;
	}

	public EventVO create(EventVO event) {
		if (event == null)
			throw new RequiredObjectIsNullException();

		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info("Creating one event!");
		var entity = DozerMapper.parseObject(event, Event.class);
		entity.setCreatedByUser(userDetails);
		var vo = DozerMapper.parseObject(repository.save(entity), EventVO.class);
		vo.add(linkTo(methodOn(EventController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public EventVO update(EventVO event) {

		if (event == null)
			throw new RequiredObjectIsNullException();

		logger.info("Updating one event!");

		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var entity = repository.findById(event.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		if (userDetails.getRoles().contains("ADMIN") ||
				userDetails.getRoles().contains("MANAGER") ||
				entity.getCreatedByUser().getId().equals(userDetails.getId())) {
			entity.setLocationName(event.getLocationName());
			entity.setStreet(event.getStreet());
			entity.setNeighborhood(event.getNeighborhood());
			entity.setCity(event.getCity());
			entity.setState(event.getState());
			entity.setZipCode(event.getZipCode());
			entity.setComplement(event.getComplement());
			entity.setEventName(event.getEventName());
			entity.setDescription(event.getDescription());
			entity.setStartDate(event.getStartDate());
			entity.setEndDate(event.getEndDate());
			entity.setTicketTitle(event.getTicketTitle());
			entity.setQuantity(event.getQuantity());
			entity.setTickePrice(event.getTickePrice());
			entity.setStartOfSales(event.getStartOfSales());
			entity.setEndOfSales(event.getEndOfSales());
			entity.setMinPurchaseQuantity(event.getMinPurchaseQuantity());
			entity.setMaxPurchaseQuantity(event.getMaxPurchaseQuantity());
			entity.setEventStatus(event.getEventStatus());
		} else {
			throw new AccessDeniedException("You do not have permission to edit this event.");
		}
		var vo = DozerMapper.parseObject(repository.save(entity), EventVO.class);
		vo.add(linkTo(methodOn(EventController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
}