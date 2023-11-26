package br.com.pontotrilha.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.controllers.TicketController;
import br.com.pontotrilha.data.vo.v1.TicketVO;
import br.com.pontotrilha.exceptions.ResourceNotFoundException;
import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.repositories.TicketRepository;

@Service
public class TicketServices {

	private Logger logger = Logger.getLogger(TicketServices.class.getName());

	@Autowired
	TicketRepository repository;

	public List<TicketVO> findAll() {

		logger.info("Finding all tickets!");

		var tickets = DozerMapper.parseListObjects(repository.findAll(), TicketVO.class);
		tickets
				.stream()
				.forEach(p -> p.add(linkTo(methodOn(TicketController.class).findById(p.getKey())).withSelfRel()));
		return tickets;
	}

	public TicketVO findById(Long id) {

		logger.info("Finding one ticket!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, TicketVO.class);
		vo.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
		return vo;
	}

	/*public TicketVO create(TicketVO ticket) {
		if (ticket == null)
			throw new RequiredObjectIsNullException();

		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info("Creating one ticket!");
		var entity = DozerMapper.parseObject(ticket, Ticket.class);
		entity.setPurchasedByUserId(userDetails);
		var vo = DozerMapper.parseObject(repository.save(entity), TicketVO.class);
		vo.add(linkTo(methodOn(TicketController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public TicketVO update(TicketVO ticket) {

		if (ticket == null)
			throw new RequiredObjectIsNullException();

		logger.info("Updating one ticket!");

		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var entity = repository.findById(ticket.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		if (userDetails.getRoles().contains("ADMIN") ||
				userDetails.getRoles().contains("MANAGER") ||
				entity.getCreatedByUser().getId().equals(userDetails.getId())) {
			entity.setLocationName(ticket.getLocationName());
			entity.setStreet(ticket.getStreet());
			entity.setNeighborhood(ticket.getNeighborhood());
			entity.setCity(ticket.getCity());
			entity.setState(ticket.getState());
			entity.setZipCode(ticket.getZipCode());
			entity.setComplement(ticket.getComplement());
			entity.setTicketName(ticket.getTicketName());
			entity.setDescription(ticket.getDescription());
			entity.setStartDate(ticket.getStartDate());
			entity.setEndDate(ticket.getEndDate());
			entity.setTicketTitle(ticket.getTicketTitle());
			entity.setQuantity(ticket.getQuantity());
			entity.setTickePrice(ticket.getTickePrice());
			entity.setStartOfSales(ticket.getStartOfSales());
			entity.setEndOfSales(ticket.getEndOfSales());
			entity.setMinPurchaseQuantity(ticket.getMinPurchaseQuantity());
			entity.setMaxPurchaseQuantity(ticket.getMaxPurchaseQuantity());
			entity.setTicketStatus(ticket.getTicketStatus());
		} else {
			throw new AccessDeniedException("You do not have permission to edit this ticket.");
		}
		var vo = DozerMapper.parseObject(repository.save(entity), TicketVO.class);
		vo.add(linkTo(methodOn(TicketController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}*/
}