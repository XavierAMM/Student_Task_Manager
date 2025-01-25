package Views.Panels;

import Controllers.MateriaController;
import Controllers.MateriaEstudianteController;
import Controllers.UsuarioController;
import Models.Materia;
import Models.MateriaEstudiante;
import Views.FrameMain;
import java.awt.Color;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PanelTodasMaterias extends javax.swing.JPanel {
    // Variables
    private MateriaController _mc = MateriaController.getInstance();
    private MateriaEstudianteController _mec = MateriaEstudianteController.getInstance();
    private int modo; // 1 = Estudiante 1 = Docente
    private FrameMain parent;
    
    
    /**
     * Creates new form PanelTodasMaterias
     */
    public PanelTodasMaterias(FrameMain parent) {
        initComponents();
        this.parent = parent;
        this.modo = UsuarioController.getMainUser().getPersona().getIdRol();
        agregarMaterias();
        
    }
    
    private void agregarMaterias(){  
        pnlMaterias.removeAll();
        List<Materia> materias;
        
        if(modo == 1) // Es estudiante = obtener materias segun su ID
            materias = _mec.getMateriasByIdEstudiante(UsuarioController.getMainUser().getIdPersona());
        else // Es Docente = Obtener materias según idDocente de cada materia
            materias = _mc.getMateriasByIdDocente(UsuarioController.getMainUser().getIdPersona());        
        
        int spacing = 20;
        
        for (Materia m : materias){
            JPanel miniPanel = crearPanelMateria(m);
            pnlMaterias.add(miniPanel);            

            // Agregar separación entre los elementos
            pnlMaterias.add(Box.createVerticalStrut(spacing)); // Espacio de 10px
        }        
        // Actualizar y redibujar
        pnlMaterias.revalidate();
        pnlMaterias.repaint();
    }
    
    private JPanel crearPanelMateria(Materia materia){
        JPanel panelMateria1;
        panelMateria1 = new javax.swing.JPanel();
        panelMateria1.setBackground(new java.awt.Color(0, 102, 102));
        panelMateria1.setMaximumSize(new java.awt.Dimension(342, 51));
        panelMateria1.setMinimumSize(new java.awt.Dimension(342, 51));
        panelMateria1.setName(""+materia.getIdMateria());
        panelMateria1.setPreferredSize(new java.awt.Dimension(342, 51));
        panelMateria1.setLayout(new java.awt.GridBagLayout());

        JLabel lblMateria;
        lblMateria = new javax.swing.JLabel();
        lblMateria.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblMateria.setForeground(new java.awt.Color(255, 255, 255));
        lblMateria.setText(materia.getNombre());
        lblMateria.setMaximumSize(new java.awt.Dimension(302, 51));
        lblMateria.setMinimumSize(new java.awt.Dimension(302, 51));
        lblMateria.setPreferredSize(new java.awt.Dimension(302, 51));
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        panelMateria1.add(lblMateria, gridBagConstraints);
        agregarEventoClick(panelMateria1);
        agregarEventoHover(panelMateria1);
        
        return panelMateria1;
    }
    
    private void agregarEventoClick(JPanel panel) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String id = panel.getName(); // Obtener el name del panel (idMateria)
                parent.initializePanel(new PanelMateria(Integer.parseInt(id), parent));
            }
        });    
    }
    private void agregarEventoHover(JPanel panel) {
        // Guardar el color original del panel
        final Color colorOriginal = panel.getBackground();
        final Color colorHover = colorOriginal.brighter(); // Color más claro para el hover

        // Añadir un MouseListener para detectar el hover
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Cambiar el color del panel cuando el mouse entra
                panel.setBackground(colorHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restaurar el color original cuando el mouse sale
                panel.setBackground(colorOriginal);
            }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        pnlMaterias = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(498, 502));
        setMinimumSize(new java.awt.Dimension(498, 502));
        setPreferredSize(new java.awt.Dimension(498, 502));

        panelEncabezado.setBackground(new java.awt.Color(255, 102, 204));
        panelEncabezado.setMaximumSize(new java.awt.Dimension(498, 42));
        panelEncabezado.setMinimumSize(new java.awt.Dimension(498, 42));
        panelEncabezado.setPreferredSize(new java.awt.Dimension(498, 42));
        panelEncabezado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Materias");
        panelEncabezado.add(jLabel1);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        pnlMaterias.setBackground(new java.awt.Color(255, 255, 255));
        pnlMaterias.setForeground(new java.awt.Color(255, 255, 255));
        pnlMaterias.setMaximumSize(new java.awt.Dimension(342, 401));
        pnlMaterias.setMinimumSize(new java.awt.Dimension(342, 401));
        pnlMaterias.setLayout(new javax.swing.BoxLayout(pnlMaterias, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(pnlMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlMaterias, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel pnlMaterias;
    // End of variables declaration//GEN-END:variables
}
