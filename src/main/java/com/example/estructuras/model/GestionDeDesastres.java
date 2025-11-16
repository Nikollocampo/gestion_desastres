package com.example.estructuras.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GestionDeDesastres {
    private String nombre;
    private List<Recurso> recursos = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Equipo> equipos = new ArrayList<>();
    private List<Desastre> desastres = new ArrayList<>();

    public GestionDeDesastres(String nombre) {
        this.nombre = nombre;
    }

    // ==================== CRUD RECURSOS ====================
    public boolean agregarRecurso(Recurso recurso) {
        if (recurso == null || recurso.getId() == null) return false;
        if (obtenerRecursoPorId(recurso.getId()) != null) return false;
        return recursos.add(recurso);
    }

    public Recurso obtenerRecursoPorId(String id) {
        if (id == null) return null;
        for (Recurso r : recursos) {
            if (id.equals(r.getId())) return r;
        }
        return null;
    }

    public boolean actualizarRecurso(Recurso recursoActualizado) {
        if (recursoActualizado == null || recursoActualizado.getId() == null) return false;
        for (int i = 0; i < recursos.size(); i++) {
            if (recursoActualizado.getId().equals(recursos.get(i).getId())) {
                recursos.set(i, recursoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarRecurso(String id) {
        if (id == null) return false;
        Iterator<Recurso> it = recursos.iterator();
        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Recurso> listarRecursos() {
        return new ArrayList<>(recursos);
    }

    // ==================== CRUD USUARIOS ====================
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        if (obtenerUsuarioPorId(usuario.getId()) != null) return false;
        return usuarios.add(usuario);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        if (id == null) return null;
        for (Usuario u : usuarios) {
            if (id.equals(u.getId())) return u;
        }
        return null;
    }

    public boolean actualizarUsuario(Usuario usuarioActualizado) {
        if (usuarioActualizado == null || usuarioActualizado.getId() == null) return false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarioActualizado.getId().equals(usuarios.get(i).getId())) {
                usuarios.set(i, usuarioActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarUsuario(String id) {
        if (id == null) return false;
        Iterator<Usuario> it = usuarios.iterator();
        while (it.hasNext()) {
            if (id.equals(it.next().getId())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // ==================== CRUD EQUIPOS ====================
    public boolean agregarEquipo(Equipo equipo) {
        if (equipo == null || equipo.getIdEquipo() == null) return false;
        if (obtenerEquipoPorId(equipo.getIdEquipo()) != null) return false;
        return equipos.add(equipo);
    }

    public Equipo obtenerEquipoPorId(String idEquipo) {
        if (idEquipo == null) return null;
        for (Equipo e : equipos) {
            if (idEquipo.equals(e.getIdEquipo())) return e;
        }
        return null;
    }

    public boolean actualizarEquipo(Equipo equipoActualizado) {
        if (equipoActualizado == null || equipoActualizado.getIdEquipo() == null) return false;
        for (int i = 0; i < equipos.size(); i++) {
            if (equipoActualizado.getIdEquipo().equals(equipos.get(i).getIdEquipo())) {
                equipos.set(i, equipoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEquipo(String idEquipo) {
        if (idEquipo == null) return false;
        Iterator<Equipo> it = equipos.iterator();
        while (it.hasNext()) {
            if (idEquipo.equals(it.next().getIdEquipo())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Equipo> listarEquipos() {
        return new ArrayList<>(equipos);
    }

    // ==================== CRUD DESASTRES ====================
    public boolean agregarDesastre(Desastre desastre) {
        if (desastre == null || desastre.getIdDesastre() == null) return false;
        if (obtenerDesastrePorId(desastre.getIdDesastre()) != null) return false;
        return desastres.add(desastre);
    }

    public Desastre obtenerDesastrePorId(String idDesastre) {
        if (idDesastre == null) return null;
        for (Desastre d : desastres) {
            if (idDesastre.equals(d.getIdDesastre())) return d;
        }
        return null;
    }

    public boolean actualizarDesastre(Desastre desastreActualizado) {
        if (desastreActualizado == null || desastreActualizado.getIdDesastre() == null) return false;
        for (int i = 0; i < desastres.size(); i++) {
            if (desastreActualizado.getIdDesastre().equals(desastres.get(i).getIdDesastre())) {
                desastres.set(i, desastreActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarDesastre(String idDesastre) {
        if (idDesastre == null) return false;
        Iterator<Desastre> it = desastres.iterator();
        while (it.hasNext()) {
            if (idDesastre.equals(it.next().getIdDesastre())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Desastre> listarDesastres() {
        return new ArrayList<>(desastres);
    }

}
