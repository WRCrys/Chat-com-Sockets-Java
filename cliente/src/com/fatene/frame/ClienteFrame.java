/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fatene.frame;

import com.fatene.bean.ChatMessage;
import com.fatene.bean.ChatMessage.Action;
import com.fatene.service.ClienteService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import com.fatene.persistência.conexaomysql;
import com.fatene.persistência.conexaopostgresql;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.Silver;
import com.jgoodies.looks.plastic.theme.SkyBlue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author cryst
 */
public class ClienteFrame extends javax.swing.JFrame {

    private Socket socket;
    private ChatMessage message;
    private ClienteService service;
    
    Connection conmysql;
    Connection conpost;
    PreparedStatement psmt;
    ResultSet rs;

    public ClienteFrame() throws Exception {
        initComponents();
        conmysql = conexaomysql.conectabdmysql();
        conpost = conexaopostgresql.conectabdpostegresql();
    }

    private class ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            try {
                ChatMessage message = null;
                while ((message = (ChatMessage) input.readObject()) != null) {
                    Action action = message.getAction();
                    if (action.equals(Action.CONNECT)) {
                        connected(message);
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnected();
                        socket.close();
                    } else if (action.equals(Action.SEND_ONE)) {
                        receive(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void connected(ChatMessage message) {
        if (message.getTxt().equals("NO")) {
            this.txtnome.setText("");
            JOptionPane.showMessageDialog(this, "Conexão não realizada.\n Tente novamente com outro novo nome!");
            return;
        }
        this.message = message;
        this.conectar.setEnabled(false);
        this.txtnome.setEnabled(false);
        this.sair.setEnabled(true);
        this.txtAreaRecive.setEnabled(true);
        this.txtSend.setEnabled(true);
        this.enviar.setEnabled(true);
        this.limpar.setEnabled(true);
        this.historico.setEnabled(true);
       

        JOptionPane.showMessageDialog(this, "Você está conectádo no chat");
    }

    private void disconnected() {
        this.conectar.setEnabled(true);
        this.txtnome.setEnabled(true);

        this.sair.setEnabled(false);
        this.txtAreaRecive.setEnabled(false);
        this.txtSend.setEnabled(false);
        this.enviar.setEnabled(false);
        this.limpar.setEnabled(false);
        this.historico.setEnabled(false);
        

        JOptionPane.showMessageDialog(this, "Você saiu do chat");

    }

    private void receive(ChatMessage message) {
        this.txtAreaRecive.append(message.getNome() + " diz:" + message.getTxt() + "\n");

    }

    private void refreshOnlines(ChatMessage message) {
        System.out.println(message.getSetOnlines().toString());
        
        Set<String> names = message.getSetOnlines();
        
        names.remove(message.getNome());
        
        String[] array = (String[]) names.toArray(new String[names.size()]);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtnome = new javax.swing.JTextField();
        conectar = new javax.swing.JButton();
        sair = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaRecive = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSend = new javax.swing.JTextArea();
        enviar = new javax.swing.JButton();
        limpar = new javax.swing.JButton();
        historico = new javax.swing.JButton();
        jl_hora = new javax.swing.JLabel();
        jl_data = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat 4.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Conectar"));

        conectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fatene/icons/OK.png"))); // NOI18N
        conectar.setText("Conectar");
        conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarActionPerformed(evt);
            }
        });

        sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fatene/icons/Exit.png"))); // NOI18N
        sair.setText("Sair");
        sair.setEnabled(false);
        sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtnome, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(conectar)
                .addGap(32, 32, 32)
                .addComponent(sair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conectar)
                    .addComponent(sair))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAreaRecive.setEditable(false);
        txtAreaRecive.setColumns(20);
        txtAreaRecive.setRows(5);
        txtAreaRecive.setEnabled(false);
        jScrollPane1.setViewportView(txtAreaRecive);

        txtSend.setColumns(20);
        txtSend.setRows(5);
        txtSend.setEnabled(false);
        jScrollPane2.setViewportView(txtSend);

        enviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fatene/icons/OK.png"))); // NOI18N
        enviar.setText("Enviar");
        enviar.setEnabled(false);
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fatene/icons/edit_clear.png"))); // NOI18N
        limpar.setText("Limpar");
        limpar.setEnabled(false);
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        historico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fatene/icons/List.png"))); // NOI18N
        historico.setText("Histórico");
        historico.setEnabled(false);
        historico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(enviar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(limpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(historico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jl_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jl_data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enviar)
                    .addComponent(limpar)
                    .addComponent(historico)
                    .addComponent(jl_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_data, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 30, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void conectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarActionPerformed
        String nome = this.txtnome.getText();
        if (!nome.isEmpty()) {
            this.message = new ChatMessage();
            this.message.setAction(Action.CONNECT);
            this.message.setNome(nome);

            this.service = new ClienteService();
            this.socket = this.service.connect();

            new Thread(new ListenerSocket(this.socket)).start();

            this.service.send(message);
        }
    }//GEN-LAST:event_conectarActionPerformed

    private void sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairActionPerformed
        this.message.setAction(Action.DISCONNECT);
        this.service.send(this.message);
        disconnected();
    }//GEN-LAST:event_sairActionPerformed

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
       String text = this.txtSend.getText();
       String nome = this.message.getNome();
       String sql = "Insert into historico (hora,data_msg,nome_msg,mensagem)values(?,?,?,?)";
              
       if(!text.isEmpty()){
           
       this.message = new ChatMessage();
       this.message.setNome(nome);
       this.message.setTxt(text);
       this.message.setAction(Action.SEND_ALL);
       
       this.txtAreaRecive.append("Você disse: "+ text+"\n");
       this.service.send(this.message);
       
       try{
            psmt = conmysql.prepareStatement(sql);
            psmt.setString(1, jl_hora.getText());
            psmt.setString(2, jl_data.getText());
            psmt.setString(3, nome);
            psmt.setString(4, text);
            psmt.execute();
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
       try{
            psmt = conpost.prepareStatement(sql);
            psmt.setString(1, jl_hora.getText());
            psmt.setString(2, jl_data.getText());
            psmt.setString(3, nome);
            psmt.setString(4, text);
            psmt.execute();
        }catch(SQLException error){
            JOptionPane.showMessageDialog(null, error);
        }
       }
       this.txtSend.setText("");
    }//GEN-LAST:event_enviarActionPerformed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        this.txtSend.setText("");
    }//GEN-LAST:event_limparActionPerformed

    private void historicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historicoActionPerformed
        try{
            HistoricoFrame hf = new HistoricoFrame();
            hf.setVisible(true);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao abrir o histórico");
        }
    }//GEN-LAST:event_historicoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date data_do_sistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        jl_data.setText(formato.format(data_do_sistema));

        Timer timer = new Timer(1000, new hora());
        timer.start();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton conectar;
    private javax.swing.JButton enviar;
    private javax.swing.JButton historico;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jl_data;
    private javax.swing.JLabel jl_hora;
    private javax.swing.JButton limpar;
    private javax.swing.JButton sair;
    private javax.swing.JTextArea txtAreaRecive;
    private javax.swing.JTextArea txtSend;
    private javax.swing.JTextField txtnome;
    // End of variables declaration//GEN-END:variables
class hora implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
        Calendar now = Calendar.getInstance();
        jl_hora.setText(String.format("%1$tH:%1$tM:%1$tS", now));
    }
    }
}
