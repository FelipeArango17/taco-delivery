package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.Taco;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TacoService {
    private final List<Taco> catalogo = new ArrayList<>();

    public TacoService() {
        catalogo.add(new Taco("Taco al Pastor", "pequeño", 15));
        catalogo.add(new Taco("Taco de Asada", "normal", 20));
    }

    public List<Taco> listarTacos() {
        return catalogo;
    }
}
