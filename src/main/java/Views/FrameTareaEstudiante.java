package Views;

import Controllers.EntregaController;
import Controllers.UsuarioController;
import Models.Configurations;
import Models.Entrega;
import Models.Tarea;
import Views.Panels.PanelCalificacionTareas;
import Views.Panels.PanelTareas;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FrameTareaEstudiante extends javax.swing.JFrame {
    // Variables
    private Tarea tarea;
    private EntregaController _ec = EntregaController.getInstance();
    private int modo;  
    private Entrega entrega = null;
    private PanelTareas parent;
    
    /** Creates new form FrameTarea */
    public FrameTareaEstudiante(Tarea tarea, PanelTareas parent) {
        initComponents();
        Configurations.setApplicationIcon(this);
        this.parent = parent;
        this.tarea = tarea;        
        initializeView();
    }
    
    public FrameTareaEstudiante(Tarea tarea) {
        initComponents();
        Configurations.setApplicationIcon(this);
        this.parent = null;
        this.tarea = tarea;        
        initializeView();
    }
    
    public final void initializeView(){        
        this.modo = analizarModo();        
        cargarDatosTarea();
        
        // Elementos Base
        pnlScore.setVisible(true);
        btnAggEntrega.setText("Agregar Entrega");
        btnAggEntrega.setBackground(new Color(0,153,153));
        btnAggEntrega.setVisible(true);
        btnAggEntrega.setEnabled(true);
        btnBorrarEntrega.setVisible(true);
        btnBorrarEntrega.setEnabled(true);
        lblInfo.setVisible(true);
        lblInfo.setText("Se ha superado la fecha límite. No se permiten más entregas.");
        
        if(modo==1){
            // 1. Tarea no atrasada, no entregada
            btnBorrarEntrega.setVisible(false);
            pnlScore.setVisible(false);            
            lblInfo.setVisible(false);
        }
        if(modo==2){
            // 2. Tarea no atrasada, entregada            
            btnAggEntrega.setText("Ver Entrega");
            btnAggEntrega.setBackground(new Color(0,51,51));
            lblInfo.setVisible(false);
        }
        if(modo==3){
            // 3. Tarea atrasada, no entregada            
            btnBorrarEntrega.setVisible(false);
            pnlScore.setVisible(false);
            btnAggEntrega.setEnabled(false);            
        }
        if(modo==4){
            // 4. Tarea atrasada, entregada            
            pnlScore.setVisible(true);
            btnBorrarEntrega.setEnabled(false);
            btnAggEntrega.setText("Ver Entrega");
            btnAggEntrega.setBackground(new Color(0,51,51));            
        }
        
        if(tarea.getFechaCreacion().isAfter(LocalDateTime.now())){
            btnAggEntrega.setVisible(false);
            lblInfo.setText("Aún no se permiten entregas.");
        }
        
        if(entrega != null && entrega.getCalificacion() >= 0){
            btnBorrarEntrega.setEnabled(false);
            lblInfo.setText("Tarea Calificada. No se permiten cambios.");
            modo = 4;
        }
            
        
    }
    
    private int analizarModo(){
        /*
        Los modos se clasificarán en:
        1. Tarea no atrasada, no entregada
        2. Tarea no atrasada, entregada
        3. Tarea atrasada, no entregada
        4. Tarea atrasada, entregada
        */        
        int idEstudiante = UsuarioController.getMainUser().getIdUsuario();
        entrega = _ec.getEntregaByIdTareaAndIdEstudiante(tarea.getIdTarea(), idEstudiante);
        
        if(tarea.getFechaEntrega().isAfter(LocalDateTime.now()) && entrega == null ) return 1;            
        if(tarea.getFechaEntrega().isAfter(LocalDateTime.now()) && entrega!= null) return 2;
        if(LocalDateTime.now().isAfter(tarea.getFechaEntrega()) && entrega == null) return 3;
        if(LocalDateTime.now().isAfter(tarea.getFechaEntrega()) && entrega != null) return 4;
        
        JOptionPane.showMessageDialog(this, "Ha ocurrido un error. Inténtelo más tarde.", "Error", JOptionPane.ERROR_MESSAGE);;
        this.dispose();
        return 0;
    }
    
    private void cargarDatosTarea(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        lblFechaApertura.setText(tarea.getFechaCreacion().format(dtf));
        lblFechaEntrega.setText(tarea.getFechaEntrega().format(dtf));
        txtTitle.setText(tarea.getTitulo());
        txtDescripcion.setText(tarea.getDescripcion());
        if(entrega!=null){
            int calificacion = entrega.getCalificacion();
            String msgCalificacion;        
            if(calificacion == -1) msgCalificacion = "Sin calificar";
            else msgCalificacion = ""+calificacion+"/100";
            lblCalificacion.setText(msgCalificacion);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlEncabezado = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlFechas = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFechaApertura = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblFechaEntrega = new javax.swing.JLabel();
        pnlTitle = new javax.swing.JPanel();
        txtTitle = new javax.swing.JTextField();
        pnlDescripcion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        pnlArchivos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlScore = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblCalificacion = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        pnlBotonesEnvio = new javax.swing.JPanel();
        btnAggEntrega = new javax.swing.JButton();
        btnBorrarEntrega = new javax.swing.JButton();
        pnlInfo = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("STM - Tarea");
        setAlwaysOnTop(true);
        setResizable(false);
        setSize(new java.awt.Dimension(367, 492));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        pnlEncabezado.setBackground(new java.awt.Color(0, 153, 153));
        pnlEncabezado.setMinimumSize(new java.awt.Dimension(337, 41));
        pnlEncabezado.setPreferredSize(new java.awt.Dimension(337, 41));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Tarea");
        pnlEncabezado.add(lblTitle);

        getContentPane().add(pnlEncabezado);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        pnlFechas.setBackground(new java.awt.Color(255, 255, 255));
        pnlFechas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 15));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Fecha de Apertura:");
        jPanel4.add(jLabel1);

        lblFechaApertura.setText("12/12/12 23:59");
        jPanel4.add(lblFechaApertura);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Fecha de Entrega:");
        jPanel4.add(jLabel2);

        lblFechaEntrega.setText("12/12/12 23:59");
        jPanel4.add(lblFechaEntrega);

        pnlFechas.add(jPanel4);

        jPanel1.add(pnlFechas);

        pnlTitle.setBackground(new java.awt.Color(255, 255, 255));

        txtTitle.setEditable(false);
        txtTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTitle.setMaximumSize(new java.awt.Dimension(291, 30));
        txtTitle.setMinimumSize(new java.awt.Dimension(291, 30));
        txtTitle.setPreferredSize(new java.awt.Dimension(291, 30));
        pnlTitle.add(txtTitle);

        jPanel1.add(pnlTitle);

        pnlDescripcion.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(291, 115));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(291, 115));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(291, 115));

        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setMaximumSize(new java.awt.Dimension(291, 115));
        txtDescripcion.setMinimumSize(new java.awt.Dimension(291, 115));
        txtDescripcion.setPreferredSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setViewportView(txtDescripcion);

        pnlDescripcion.add(jScrollPane1);

        jPanel1.add(pnlDescripcion);

        pnlArchivos.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setMaximumSize(new java.awt.Dimension(291, 78));
        jPanel3.setMinimumSize(new java.awt.Dimension(291, 78));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Archivos subidos...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pnlArchivos.add(jPanel3);

        jPanel1.add(pnlArchivos);

        pnlScore.setBackground(new java.awt.Color(255, 255, 255));
        pnlScore.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Calificación:");
        jPanel2.add(jLabel4);

        lblCalificacion.setText("100/100");
        lblCalificacion.setToolTipText("");
        jPanel2.add(lblCalificacion);

        pnlScore.add(jPanel2);

        jPanel1.add(pnlScore);

        pnlBotones.setBackground(new java.awt.Color(255, 255, 255));

        pnlBotonesEnvio.setBackground(new java.awt.Color(255, 255, 255));
        pnlBotonesEnvio.setMaximumSize(new java.awt.Dimension(256, 49));
        pnlBotonesEnvio.setMinimumSize(new java.awt.Dimension(256, 49));

        btnAggEntrega.setBackground(new java.awt.Color(0, 153, 153));
        btnAggEntrega.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAggEntrega.setForeground(new java.awt.Color(255, 255, 255));
        btnAggEntrega.setText("Agregar Entrega");
        btnAggEntrega.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAggEntrega.setBorderPainted(false);
        btnAggEntrega.setMaximumSize(new java.awt.Dimension(113, 37));
        btnAggEntrega.setMinimumSize(new java.awt.Dimension(113, 37));
        btnAggEntrega.setPreferredSize(new java.awt.Dimension(113, 37));
        btnAggEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAggEntregaMouseClicked(evt);
            }
        });

        btnBorrarEntrega.setBackground(new java.awt.Color(51, 51, 51));
        btnBorrarEntrega.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBorrarEntrega.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarEntrega.setText("Borrar Entrega");
        btnBorrarEntrega.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBorrarEntrega.setBorderPainted(false);
        btnBorrarEntrega.setMaximumSize(new java.awt.Dimension(113, 37));
        btnBorrarEntrega.setMinimumSize(new java.awt.Dimension(113, 37));
        btnBorrarEntrega.setPreferredSize(new java.awt.Dimension(113, 37));
        btnBorrarEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarEntregaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotonesEnvioLayout = new javax.swing.GroupLayout(pnlBotonesEnvio);
        pnlBotonesEnvio.setLayout(pnlBotonesEnvioLayout);
        pnlBotonesEnvioLayout.setHorizontalGroup(
            pnlBotonesEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesEnvioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAggEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrarEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        pnlBotonesEnvioLayout.setVerticalGroup(
            pnlBotonesEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBotonesEnvioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBotonesEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBorrarEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAggEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlBotones.add(pnlBotonesEnvio);

        jPanel1.add(pnlBotones);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));

        lblInfo.setForeground(new java.awt.Color(51, 51, 51));
        lblInfo.setText("Se ha superado la fecha límite. No se permiten más entregas.");
        pnlInfo.add(lblInfo);

        jPanel1.add(pnlInfo);

        getContentPane().add(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBorrarEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarEntregaActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea borrar su entrega?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION){
            _ec.deleteEntrega(entrega.getIdEntrega());
            JOptionPane.showMessageDialog(this, "Entrega borrada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);            
            initializeView();
        }
    }//GEN-LAST:event_btnBorrarEntregaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(parent!=null) parent.agregarTareas();
        
    }//GEN-LAST:event_formWindowClosing

    private void btnAggEntregaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAggEntregaMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed() && btnAggEntrega.isEnabled()) {
            evt.consume(); // Evitar múltiples activaciones
            JFrame fe = new JFrame();        
            if(modo==1) fe = new FrameEntrega(tarea, this);        
            if(modo==2) fe = new FrameEntrega(entrega, false, this);                    
            if(modo==4) fe = new FrameEntrega(entrega, true, this);
            fe.setVisible(true);    
        }
    }//GEN-LAST:event_btnAggEntregaMouseClicked

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAggEntrega;
    private javax.swing.JButton btnBorrarEntrega;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCalificacion;
    private javax.swing.JLabel lblFechaApertura;
    private javax.swing.JLabel lblFechaEntrega;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlArchivos;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBotonesEnvio;
    private javax.swing.JPanel pnlDescripcion;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlFechas;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlScore;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

}
