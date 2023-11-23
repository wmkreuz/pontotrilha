package br.com.pontotrilha.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_permission")
public class UserPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_user")
	private Long idUser;

	@Column(name = "id_permission")
	private Long idPermission;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdPermission() {
		return idPermission;
	}

	public void setIdPermission(Long idPermission) {
		this.idPermission = idPermission;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((idPermission == null) ? 0 : idPermission.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPermission other = (UserPermission) obj;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (idPermission == null) {
			if (other.idPermission != null)
				return false;
		} else if (!idPermission.equals(other.idPermission))
			return false;
		return true;
	}
}