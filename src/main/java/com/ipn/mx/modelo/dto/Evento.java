/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author frida
 */
public class Evento implements Serializable {

    private int idEvento;
    private String nombreEvento;
    private String sede;
    private Date fechaInicio;
    private Date fechaFin;

    public Evento() {

    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaTermino(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idEvento=").append(getIdEvento()).append("\n");
        sb.append(", nombreEvento=").append(nombreEvento).append("\n");
        sb.append(", sede=").append(sede).append("\n");
        sb.append(", fechaInicio=").append(fechaInicio).append("\n");
        sb.append(", fechaFin=").append(fechaFin).append("\n");
        return sb.toString();
    }   
    public static void main(String[] args)   {
        Evento e= new Evento();
        e.setIdEvento(2);
        e.setNombreEvento("actualizacion 3100");
        e.setSede("act");
        e.setFechaInicio(Date.valueOf("2020-10-25"));
        e.setFechaTermino(Date.valueOf("2020-10-06"));
        //System.out.println(e);
        
        String user = "jorgiux";
        String pwd = "jorgiux1234"; 
        //String url = "jdbc:mysql://localhost:3306/3CM9"; /* */
        String url = "jdbc:mysql://localhost:3306/3CM9?allowPublicKeyRetrieval=true";
        //String url = "jdbc:mysql://localhost:3306/3CM9?serverTimezone=America/Mexico_City&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false?allowPublicKeyRetrieval=true";
        String driverMySql = "com.mysql.cj.jdbc.Driver";
        
        Connection conexion = null;
        try {
            Class.forName(driverMySql);
            conexion = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
        insertar(conexion, e);
        PreparedStatement ps = null;
        ResultSet rs = null;
        String SQL_SELECT="select * from Evento where idEvento = ?";
        try {
            ps = conexion.prepareStatement(SQL_SELECT);
            ps.setInt(1, e.getIdEvento());
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                
                e.setIdEvento(rs.getInt("idEvento"));
                e.setNombreEvento(rs.getString("nombreEvento"));
                //..
                
                
            }
            System.out.println(e);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void borrar(Connection conexion, Evento e) {
        //actualizar(conexion, e);
        PreparedStatement ps;
        String SQL_DELETE="delete from Evento where idEvento = ?";
        try {
            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setInt(1, e.getIdEvento());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void actualizar(Connection conexion, Evento e) {
        PreparedStatement ps =null;
        String SQL_UPDATE="update Evento set nombreEvento= ?, sede = ?, fechaInicio = ?, fechaFin = ? where idEvento = ?";
        try {
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, e.getNombreEvento());
            ps.setString(2, e.getSede());
            ps.setDate(3, e.getFechaInicio());
            ps.setDate(4, e.getFechaFin());
            ps.setInt(5, e.getIdEvento());
            ps.executeUpdate();
            //insertar(conexion, e);
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertar(Connection conexion, Evento e) {
        PreparedStatement ps = null;
        String SQL_INSERT="insert into Evento(nombreEvento,sede,fechaInicio,fechaFin) values (?,?,?,?)"; 
        try {
            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, e.getNombreEvento());
            ps.setString(2, e.getSede());
            ps.setDate(3, e.getFechaInicio());
            ps.setDate(4, e.getFechaFin());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
