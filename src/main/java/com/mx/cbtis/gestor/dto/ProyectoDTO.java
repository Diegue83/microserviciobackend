package com.mx.cbtis.gestor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mx.cbtis.gestor.modelo.Proyectos;

/**
 * Datos de proyecto que acepta la API. Separa el contrato JSON de la entidad JPA.
 */
public record ProyectoDTO(
		@JsonProperty("id_proyecto") Integer idProyecto,
		@JsonProperty("nombre") String nombre,
		@JsonProperty("descripcion") String descripcion,
		@JsonProperty("fecha_inicio") String fechaInicio,
		@JsonProperty("fecha_finalizacion") String fechaFinalizacion,
		@JsonProperty("estado_proyecto") String estadoProyecto,
		@JsonProperty("fondo") String fondo) {

	public Proyectos toEntity() {
		return new Proyectos(idProyecto, nombre, descripcion, fechaInicio, fechaFinalizacion, estadoProyecto, fondo);
	}
}
