package com.tp.TpIntegradorBazar.controller;

import com.tp.TpIntegradorBazar.dto.ClienteDTO;
import com.tp.TpIntegradorBazar.service.ClienteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> traerClientes(){
        return ResponseEntity.ok(clienteService.traerClientes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> traerCliente(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.traerCliente(id));
    }
    
    
    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO dto){
        ClienteDTO creado = clienteService.crearCliente(dto);
        
        return ResponseEntity.created(URI.create("clientes" 
                + creado.getIdCliente())).body(creado);
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id,
                                                          @Valid @RequestBody ClienteDTO dto){
        return ResponseEntity.ok(clienteService.actualizarCliente(id, dto));      
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
    
}
