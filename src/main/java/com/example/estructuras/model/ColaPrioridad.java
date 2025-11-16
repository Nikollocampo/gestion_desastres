// java
package com.example.estructuras.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ColaPrioridad<T extends Comparable<T>> {
    private PriorityQueue<T> colaPrioridad;

    // Orden natural
    public ColaPrioridad() {
        this.colaPrioridad = new PriorityQueue<>();
    }

    // Orden personalizado
    public ColaPrioridad(Comparator<T> comparator) {
        this.colaPrioridad = new PriorityQueue<>(comparator);
    }

    // Alias compatible con el Main
    public void encolar(T elemento) {
        colaPrioridad.offer(elemento);
        System.out.println("Elemento encolado: " + elemento);
    }

    // Encolar varios elementos
    public void encolarTodos(Collection<T> elementos) {
        if (elementos == null || elementos.isEmpty()) return;
        for (T e : elementos) encolar(e);
    }

    // Método previo (se mantiene por compatibilidad)
    @Deprecated
    public void agregarElemento(T elemento) {
        encolar(elemento);
    }

    // Ver el elemento con mayor prioridad sin retirarlo
    public T verSiguiente() {
        T siguiente = colaPrioridad.peek();
        if (siguiente != null) {
            System.out.println("Siguiente en cola (sin retirar): " + siguiente);
        } else {
            System.out.println("No hay elementos en la cola.");
        }
        return siguiente;
    }

    // Atender y retirar el elemento con mayor prioridad
    public T atenderSiguiente() {
        T siguiente = colaPrioridad.poll();
        if (siguiente != null) {
            System.out.println("Atendiendo elemento: " + siguiente);
        } else {
            System.out.println("No hay elementos en la cola.");
        }
        return siguiente;
    }

    // Saber si la cola contiene un elemento
    public boolean contiene(T elemento) {
        return colaPrioridad.contains(elemento);
    }

    // Vaciar la cola
    public void vaciar() {
        colaPrioridad.clear();
        System.out.println("Cola vaciada.");
    }

    // Mostrar la cola actual (iteración no garantiza orden estricto)
    public String mostrarCola() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cola de prioridad actual:\n");
        if (colaPrioridad.isEmpty()) {
            sb.append("Vacía\n");
        } else {
            for (T elemento : colaPrioridad) {
                sb.append("- ").append(elemento).append("\n");
            }
        }
        System.out.print(sb.toString());
        return sb.toString();
    }

    // Saber si la cola está vacía
    public boolean estaVacia() {
        return colaPrioridad.isEmpty();
    }

    // Tamaño actual
    public int tam() {
        return colaPrioridad.size();
    }

    // Alias de tamaño
    public int tamanio() {
        return tam();
    }

    // Copia a lista (orden no garantizado)
    public List<T> aLista() {
        return List.copyOf(colaPrioridad);
    }
}
