package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.FileController;
import br.com.erudio.controllers.MapController;
import br.com.erudio.data.vo.v1.MapVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Map;
import br.com.erudio.repositories.MapRepository;

@Service
public class MapServices {
	
	private Logger logger = Logger.getLogger(MapServices.class.getName());
	
	@Autowired
	MapRepository repository;

	@Autowired
    private FileController fileController;

	public List<MapVO> findAll() {

		logger.info("Finding all maps!");

		var maps = DozerMapper.parseListObjects(repository.findAll(), MapVO.class);
		maps
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(MapController.class).findById(p.getKey())).withSelfRel()));
		return maps;
	}

	public MapVO findById(Long id) {
		
		logger.info("Finding one map!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, MapVO.class);
		vo.add(linkTo(methodOn(MapController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public MapVO create(MapVO map) {

		if (map == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one map!");
		var entity = DozerMapper.parseObject(map, Map.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), MapVO.class);
		vo.add(linkTo(methodOn(MapController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public MapVO update(MapVO map) {

		if (map == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one map!");
		
		var entity = repository.findById(map.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setDescription(map.getDescription());
		entity.setLatitude(map.getLatitude());
		entity.setLongitude(map.getLongitude());
		entity.setIdGoogle(map.getIdGoogle());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), MapVO.class);
		vo.add(linkTo(methodOn(MapController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one map!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		fileController.deleteFile(entity.getIdGoogle());
		repository.delete(entity);
	}
}
