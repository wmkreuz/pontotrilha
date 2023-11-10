package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.UserPermission;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {}