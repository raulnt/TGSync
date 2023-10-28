package Model.DAO;

import Model.ConexaoBD.ConexaoBD;
import Model.DTO.AlunoDTO;
import Model.DTO.EntregaDTO;
import Model.DTO.NotaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotaDAO {

    Connection connection = null;

    public int addNota(NotaDTO notaDTO){
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "INSERT INTO nota (feedback, valor, idAluno, idEntrega) VALUES(?,?,?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1,notaDTO.getFeedback());
            stmt.setDouble(2,notaDTO.getValor());
            stmt.setLong(3,notaDTO.getIdAluno());
            stmt.setLong(4,notaDTO.getIdEntrega());

            stmt.executeUpdate();

        } catch (SQLException e){
            success = 1;
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            success = 1;
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return success;
    }

    public NotaDTO getNotaPorAlunoEntrega(Long alunoId, Long entregaId){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM nota WHERE idAluno = ? and idEntrega = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,alunoId);
            stmt.setLong(2,entregaId);
            rs = stmt.executeQuery();
            while (rs.next()){
                NotaDTO notaDTO = new NotaDTO(rs.getLong("id"), rs.getString("feedback"), rs.getDouble("valor"), rs.getLong("idAluno"), rs.getLong("idEntrega"));
                return notaDTO;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public NotaDTO getNotaPorId(Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            connection = ConexaoBD.ConexaoBD();
            String sql = "SELECT * FROM nota WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                NotaDTO notaDTO = new NotaDTO(rs.getLong("id"), rs.getString("feedback"), rs.getDouble("valor"), rs.getLong("idAluno"), rs.getLong("idEntrega"));
                return notaDTO;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;

    }
    public int updateNota(NotaDTO notaDTO) {
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = ConexaoBD.ConexaoBD();

            String sql = "UPDATE nota SET feedback = ?, valor = ?, idAluno = ?, idEntrega = ? WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, notaDTO.getFeedback());
            stmt.setDouble(2, notaDTO.getValor());
            stmt.setLong(3, notaDTO.getIdAluno());
            stmt.setLong(4, notaDTO.getIdEntrega());
            stmt.setLong(5, notaDTO.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            success = 1;
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            success = 1;
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return success;
    }
}
