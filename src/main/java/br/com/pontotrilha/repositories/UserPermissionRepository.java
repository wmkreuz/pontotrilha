package br.com.pontotrilha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontotrilha.model.UserPermission;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {}