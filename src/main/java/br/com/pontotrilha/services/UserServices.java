package br.com.pontotrilha.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.controllers.UserController;
import br.com.pontotrilha.data.vo.v1.UserVO;
import br.com.pontotrilha.exceptions.RequiredObjectIsNullException;
import br.com.pontotrilha.exceptions.ResourceNotFoundException;
import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.model.User;
import br.com.pontotrilha.repositories.UserRepository;

@Service
public class UserServices {

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    UserRepository repository;

    public List<UserVO> findAll() {

        logger.info("Finding all users!");

        var users = DozerMapper.parseListObjects(repository.findAll(), UserVO.class);
        users
            .stream()
            .forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));

        return users;

    }

    public UserVO findById(Long id) {

        logger.info("Finding one user!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());

        return vo;

    }

    public UserVO create(UserVO user) {

        if(user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one user!");

        var entity = DozerMapper.parseObject(user, User.class);

        var vo = DozerMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }

    public UserVO update(UserVO user) {
        
        if(user == null) throw new RequiredObjectIsNullException();
        
        logger.info("Updating one user!");

        var entity = repository.findById(user.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setUserName(user.getUserName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());

        var vo = DozerMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }

    public void delete(Long id) {

        logger.info("deleting one user!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);

    }

}