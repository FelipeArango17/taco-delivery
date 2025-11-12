package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.*;
import com.tacodelivery.tacodelivery.repository.ItemCarritoRepository;
import com.tacodelivery.tacodelivery.repository.TacoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemCarritoService {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private TacoRepository tacoRepository;

    public List<ItemCarrito> obtenerItemsPorUsuario(User user) {
        return itemCarritoRepository.findByUser(user);
    }

    public void agregarItem(User user, Taco taco, int cantidad) {
        Taco tacoBD = tacoRepository.findByNombreAndTamaño(taco.getNombre(), taco.getTamaño())
                .orElseThrow(() -> new RuntimeException("Taco no encontrado: " + taco.getNombre() + " " + taco.getTamaño()));

        List<ItemCarrito> items = itemCarritoRepository.findByUser(user);

        for (ItemCarrito item : items) {
            if (item.getTaco().getId().equals(tacoBD.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                item.setSubtotal(item.getTaco().getPrecio() * item.getCantidad());
                itemCarritoRepository.save(item);
                return;
            }
        }

        ItemCarrito nuevo = new ItemCarrito(user, tacoBD, cantidad);
        nuevo.setSubtotal(tacoBD.getPrecio() * cantidad);
        itemCarritoRepository.save(nuevo);
    }

    public void actualizarCantidad(Long itemId, int cantidad) {
        ItemCarrito item = itemCarritoRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setCantidad(cantidad);
            item.setSubtotal(item.getTaco().getPrecio() * cantidad);
            itemCarritoRepository.save(item);
        }
    }

    // ✅ Nuevo método para cambiar el tamaño del taco dentro del carrito
    public void actualizarTamaño(Long itemId, String nuevoTamaño) {
        ItemCarrito item = itemCarritoRepository.findById(itemId).orElse(null);
        if (item != null) {
            Taco tacoActual = item.getTaco();
            Taco nuevoTaco = tacoRepository.findByNombreAndTamaño(tacoActual.getNombre(), nuevoTamaño)
                    .orElseThrow(() -> new RuntimeException("No se encontró el taco con tamaño " + nuevoTamaño));

            item.setTaco(nuevoTaco);
            item.setSubtotal(nuevoTaco.getPrecio() * item.getCantidad());
            itemCarritoRepository.save(item);
        }
    }

    public void eliminarItem(Long itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    @Transactional // ✅ Soluciona el error de "No EntityManager..."
    public void vaciarCarrito(User user) {
        itemCarritoRepository.deleteByUser(user);
    }

    public int calcularTotal(User user) {
        return itemCarritoRepository.findByUser(user)
                .stream()
                .mapToInt(ItemCarrito::getSubtotal)
                .sum();
    }
}
