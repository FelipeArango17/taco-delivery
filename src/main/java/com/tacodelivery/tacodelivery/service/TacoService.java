package com.tacodelivery.tacodelivery.service;

import com.tacodelivery.tacodelivery.model.Taco;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TacoService {
    private final List<Taco> catalogo = new ArrayList<>();

    public TacoService() {
        // Tacos al Pastor
        catalogo.add(new Taco("Tacos al Pastor", "Pequeño", 10000));
        catalogo.add(new Taco("Tacos al Pastor", "Normal", 15000));
        catalogo.add(new Taco("Tacos al Pastor", "Extragrande", 20000));

        // Tacos de Carne Asada
        catalogo.add(new Taco("Tacos de Carne Asada", "Pequeño", 11000));
        catalogo.add(new Taco("Tacos de Carne Asada", "Normal", 16000));
        catalogo.add(new Taco("Tacos de Carne Asada", "Extragrande", 21000));

        // Tacos de Pollo
        catalogo.add(new Taco("Tacos de Pollo", "Pequeño", 10000));
        catalogo.add(new Taco("Tacos de Pollo", "Normal", 15000));
        catalogo.add(new Taco("Tacos de Pollo", "Extragrande", 20000));

        // Tacos de Chorizo
        catalogo.add(new Taco("Tacos de Chorizo", "Pequeño", 11000));
        catalogo.add(new Taco("Tacos de Chorizo", "Normal", 16000));
        catalogo.add(new Taco("Tacos de Chorizo", "Extragrande", 22000));

        // Tacos de Pescado
        catalogo.add(new Taco("Tacos de Pescado", "Pequeño", 12000));
        catalogo.add(new Taco("Tacos de Pescado", "Normal", 17000));
        catalogo.add(new Taco("Tacos de Pescado", "Extragrande", 23000));

        // Tacos Veganos
        catalogo.add(new Taco("Tacos Veganos", "Pequeño", 10000));
        catalogo.add(new Taco("Tacos Veganos", "Normal", 14000));
        catalogo.add(new Taco("Tacos Veganos", "Extragrande", 19000));

        // Tacos de Barbacoa
        catalogo.add(new Taco("Tacos de Barbacoa", "Pequeño", 12000));
        catalogo.add(new Taco("Tacos de Barbacoa", "Normal", 18000));
        catalogo.add(new Taco("Tacos de Barbacoa", "Extragrande", 24000));

        // Tacos de Camarón
        catalogo.add(new Taco("Tacos de Camarón", "Pequeño", 13000));
        catalogo.add(new Taco("Tacos de Camarón", "Normal", 19000));
        catalogo.add(new Taco("Tacos de Camarón", "Extragrande", 25000));

        // Tacos Mixtos
        catalogo.add(new Taco("Tacos Mixtos", "Pequeño", 14000));
        catalogo.add(new Taco("Tacos Mixtos", "Normal", 20000));
        catalogo.add(new Taco("Tacos Mixtos", "Extragrande", 26000));

        // Tacos de Cochinita Pibil
        catalogo.add(new Taco("Tacos de Cochinita Pibil", "Pequeño", 13000));
        catalogo.add(new Taco("Tacos de Cochinita Pibil", "Normal", 19000));
        catalogo.add(new Taco("Tacos de Cochinita Pibil", "Extragrande", 25000));
    }

    public List<Taco> listarTacos() {
        return catalogo;
    }
}
