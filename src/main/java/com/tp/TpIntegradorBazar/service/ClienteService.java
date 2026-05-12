package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.ClienteDTO;
import java.util.List;

public interface ClienteService {
    
    public List<ClienteDTO> traerClientes();
    public ClienteDTO crearCliente(ClienteDTO c);
    public ClienteDTO actualizarCliente(Long id, ClienteDTO c);
    public void eliminarCliente(Long id);
    public ClienteDTO traerCliente(Long id);
    
}
