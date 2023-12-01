package br.com.pontotrilha.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.controllers.TicketController;
import br.com.pontotrilha.data.vo.v1.TicketVO;
import br.com.pontotrilha.exceptions.RequiredObjectIsNullException;
import br.com.pontotrilha.exceptions.ResourceNotFoundException;
import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.model.Ticket;
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

	public TicketVO create(TicketVO ticket) {
		if (ticket == null)
			throw new RequiredObjectIsNullException();

		logger.info("Creating one ticket!");
		var entity = DozerMapper.parseObject(ticket, Ticket.class);
		var vo = DozerMapper.parseObject(repository.save(entity), TicketVO.class);
		vo.add(linkTo(methodOn(TicketController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
}