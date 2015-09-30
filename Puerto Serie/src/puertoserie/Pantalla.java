
package puertoserie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pantalla extends javax.swing.JFrame {
   
    ExpertoModbus experto = new ExpertoModbus();
    ArrayList datos;
    DTOPantalla dto = new DTOPantalla();
    
    
    public Pantalla() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        campoIdDispositivo = new javax.swing.JTextField();
        campoDireccionInicial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoCantidadVariables = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        campoPuerto = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoResultados = new javax.swing.JTextArea();
        comboNroFuncion = new javax.swing.JComboBox();
        comboVista = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        campoTrama = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Dispositivo:");

        jLabel2.setText("Función:");

        jLabel3.setText("Dirección Inicial:");

        jLabel4.setText("Variables:");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Puerto:");

        campoPuerto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9" }));

        campoResultados.setColumns(20);
        campoResultados.setRows(5);
        jScrollPane1.setViewportView(campoResultados);

        comboNroFuncion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "6", "16" }));
        comboNroFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNroFuncionActionPerformed(evt);
            }
        });

        comboVista.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Decimal", "Binario", "Hexadecimal" }));
        comboVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVistaActionPerformed(evt);
            }
        });

        jLabel6.setText("Vista:");

        jLabel7.setText("Trama Recibida:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboVista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoPuerto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(campoCantidadVariables)
                                    .addComponent(campoDireccionInicial)
                                    .addComponent(campoIdDispositivo)
                                    .addComponent(comboNroFuncion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoTrama))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(campoIdDispositivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(comboNroFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoDireccionInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoCantidadVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(campoPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTrama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        if (campoIdDispositivo != null && comboNroFuncion != null &&
            campoDireccionInicial != null && campoCantidadVariables != null){
            
            dto.setIdDispositivo(Integer.parseInt(campoIdDispositivo.getText()));
            dto.setNroFuncion(Integer.parseInt(comboNroFuncion.getSelectedItem().toString()));
            dto.setDireccionInicial(Integer.parseInt(campoDireccionInicial.getText())-1);
            dto.setCantidadVariables(Integer.parseInt(campoCantidadVariables.getText()));
            dto.setPuerto(campoPuerto.getSelectedItem().toString());
            
//            int dispositivo = Integer.parseInt(campoIdDispositivo.getText());
//            int funcion = Integer.parseInt(comboNroFuncion.getSelectedItem().toString());
//            int inicio = Integer.parseInt(campoDireccionInicial.getText())-1;
//            int cantidad = Integer.parseInt(campoCantidadVariables.getText());
//            String puerto = campoPuerto.getSelectedItem().toString();
            if(comboNroFuncion.getSelectedItem().toString().equals("3")){
                this.dto = experto.funcionTres(dto);
            }else if (comboNroFuncion.getSelectedItem().toString().equals("6")){
                this.dto = experto.funcionSeis(dto);
            }else if (comboNroFuncion.getSelectedItem().toString().equals("16")){
                 
            }
            

            datos = dto.getDatos();
            
            campoTrama.setText(dto.getTrama());
            
            if(comboVista.getSelectedIndex() == 0)
                mostrarDec(datos);
            else if (comboVista.getSelectedIndex() == 1)
                mostrarBin(datos);
            else if (comboVista.getSelectedIndex() == 2)
                mostrarHex(datos);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void comboVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVistaActionPerformed
        if(datos != null){
            if(comboVista.getSelectedIndex() == 0)
                mostrarDec(datos);
            else if (comboVista.getSelectedIndex() == 1)
                mostrarBin(datos);
            else if (comboVista.getSelectedIndex() == 2)
                mostrarHex(datos);
        }
    }//GEN-LAST:event_comboVistaActionPerformed

    private void comboNroFuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNroFuncionActionPerformed
        if(comboNroFuncion.getSelectedItem().toString().equals("3")){
            jLabel3.setText("Dirección Inicial:");
            jLabel4.setText("Variables:");
        }else if (comboNroFuncion.getSelectedItem().toString().equals("6")){
            jLabel3.setText("Dirección:");
            jLabel4.setText("Valor:");
        }else if (comboNroFuncion.getSelectedItem().toString().equals("16")){
            jLabel3.setText("Dirección Inicial:");
            jLabel4.setText("Valores:"); 
        }
    }//GEN-LAST:event_comboNroFuncionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField campoCantidadVariables;
    private javax.swing.JTextField campoDireccionInicial;
    private javax.swing.JTextField campoIdDispositivo;
    private javax.swing.JComboBox campoPuerto;
    private javax.swing.JTextArea campoResultados;
    private javax.swing.JTextField campoTrama;
    private javax.swing.JComboBox comboNroFuncion;
    private javax.swing.JComboBox comboVista;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

public void mostrarBin(ArrayList datos){
    String datosAux="";
    for (int i = 0; i < datos.size(); i++) {
        datosAux = datosAux+"\n"+(i+1)+" ---> "+(Integer.toBinaryString((int)datos.get(i)));
    }
    campoResultados.setText(datosAux);
}

public void mostrarHex(ArrayList datos){
    String datosAux="";
    for (int i = 0; i < datos.size(); i++) {
        datosAux = datosAux+"\n"+(i+1)+" ---> "+(Integer.toHexString((int)datos.get(i)));
    }
    campoResultados.setText(datosAux);
}

public void mostrarDec(ArrayList datos){
    String datosAux="";
    for (int i = 0; i < datos.size(); i++) {
        datosAux = datosAux+"\n"+(i+1)+" ---> "+(datos.get(i).toString());
    }
    campoResultados.setText(datosAux);
}
}


