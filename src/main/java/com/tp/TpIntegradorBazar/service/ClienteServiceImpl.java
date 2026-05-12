package com.tp.TpIntegradorBazar.service;

import com.tp.TpIntegradorBazar.dto.ClienteDTO;
import com.tp.TpIntegradorBazar.exception.NotFoundException;
import com.tp.TpIntegradorBazar.mapper.Mapper;
import com.tp.TpIntegradorBazar.model.Cliente;
import com.tp.TpIntegradorBazar.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepo;
    
    @Override
    public List<ClienteDTO> traerClientes() {
        List<Cliente> clientes = clienteRepo.findAll();
        if (clientes.isEmpty()){
            throw new NotFoundException("Clientes no encontrados");
        }
        return clientes.stream().map(Mapper::toDto).toList();
    }

    @Override
    public ClienteDTO crearCliente(ClienteDTO c) {

        return Mapper.toDto(clienteRepo.save(Mapper.toEntity(c)));
        
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO c) {
        Cliente cli = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        
        cli.setNombre(c.getNombre());
        cli.setApellido(c.getApellido());
        cli.setDni(c.getDni());
        
        return Mapper.toDto(clienteRepo.save(cli));
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cli = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        
        clienteRepo.delete(cli);
    }

    @Override
    public ClienteDTO traerCliente(Long id) {
        Cliente cli = clienteRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        
        return Mapper.toDto(cli);
    }
    
}
