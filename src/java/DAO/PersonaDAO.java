/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import VO.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Labing
 */
public class PersonaDAO implements IBaseDatos<Persona> {

    @Override
    public List<Persona> findAll() throws SQLException {
        List<Persona> personas = null;
        String query = "SELECT * FROM Persona";
        Connection connection = Conexion.getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int id = 0;
            String nombre = null;
            String apelldio = null;

            while (rs.next()) {
                if (personas == null) {
                    personas = new ArrayList<Persona>();
                }

                Persona registro = new Persona();
                id = rs.getInt("cedula");
                registro.setCedula(id);

                nombre = rs.getString("nombre");
                registro.setNombre(nombre);

                apelldio = rs.getString("apellido");
                registro.setApellido(apelldio);

                personas.add(registro);
            }
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Persona");
            e.printStackTrace();
        }

        return personas;
    }

    @Override
    public boolean insert(Persona t) throws SQLException {
        int result = 0;
        Connection connection = Conexion.getConnection();
        String query = " insert into Persona" + " values (?,?,?)";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, t.getCedula());
            preparedStmt.setString(2, t.getNombre());
            preparedStmt.setString(3, t.getApellido());

            result = preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        if(result==0){
            return false;
        }else{
            return true;
        }        
    }

    @Override
    public boolean update(Persona t) throws SQLException {
        boolean result = false;
        Connection connection = Conexion.getConnection();
        String query = "update Persona set nombre = ?, apellido = ? where cedula = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, t.getCedula());
            preparedStmt.setString(2, t.getNombre());
            preparedStmt.setString(3, t.getApellido());
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delete(Persona t) throws SQLException {
        boolean result = false;
        Connection connection = Conexion.getConnection();
        String query = "delete from Persona where cedula = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, t.getCedula());
            result = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Persona find(int cedula) throws SQLException {
        Persona resultado = null;
        PreparedStatement preparedStmt = null;
        String query = "SELECT * FROM Persona where cedula = ?";
        Connection connection = Conexion.getConnection();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            preparedStmt.setInt(1, cedula);
            int id = 0;
            String nombre = null;
            String apelldio = null;

            if (rs.next()) {

                resultado = new Persona();
                id = rs.getInt("cedula");
                resultado.setCedula(id);

                nombre = rs.getString("nombre");
                resultado.setNombre(nombre);

                apelldio = rs.getString("apellido");
                resultado.setApellido(apelldio);

            }
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Persona");
            e.printStackTrace();
        }
        return resultado;
    }

}
