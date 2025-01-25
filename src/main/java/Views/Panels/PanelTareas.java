package Views.Panels;

import Controllers.EntregaController;
import Controllers.TareaController;
import Controllers.UsuarioController;
import Models.Configurations;
import Models.Entrega;
import Models.Tarea;
import Views.FrameMain;
import Views.FrameTareaDocente;
import Views.FrameTareaEstudiante;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class PanelTareas extends javax.swing.JPanel {

    // Variables
    private int modo; // 1 = Estudiante, 2 = Docente
    private int idMateria;
    private TareaController _tareaController = TareaController.getInstance();
    private EntregaController _ec = EntregaController.getInstance();
    private FrameMain parent;

    /**
     * Creates new form PanelTareas
     */
    public PanelTareas(int idMateria, FrameMain parent) {
        initComponents();
        this.idMateria = idMateria;
        this.parent = parent;
        this.modo = UsuarioController.getMainUser().getPersona().getIdRol();
        if (modo == 1) {
            btnNuevaTarea.setVisible(false);
        }
        agregarTareas();
    }

    public final void agregarTareas() {
        panelTareas.removeAll();
        List<Entrega> entregas = new ArrayList<>();
        int spacing = 20; // Espacio entre paneles        
        List<Tarea> tareas = _tareaController.getTareasByIdMateria(idMateria);
        if (modo == 1) { // Es estudiante. Evaluar la info de tarea: Pendiente / Finalizado
            int idEstudiante = UsuarioController.getMainUser().getIdPersona();
            entregas = _ec.getEntregasByIdEstudiante(idEstudiante);
        }

        for (Tarea t : tareas) {
            String informacion = "";

            if (modo == 1) {
                informacion = "Pendiente";
                if (LocalDateTime.now().isAfter(t.getFechaEntrega())) {
                    informacion = "Atrasada";
                }
                for (Entrega e : entregas) {
                    if (e.getIdTarea() == t.getIdTarea()) {
                        informacion = "Finalizada";
                    }
                }
            }

            JPanel miniPanel = createTaskElement(t, informacion);
            // Agregar el panel al panelTareas
            panelTareas.add(miniPanel);

            // Agregar separación entre los elementos
            panelTareas.add(Box.createVerticalStrut(spacing)); // Espacio de 10px
        }
        // Actualizar y redibujar
        panelTareas.revalidate();
        panelTareas.repaint();
    }

    private JPanel createTaskElement(Tarea tarea, String informacion) {
        Color colorFondo = new Color(204, 255, 255);
        if (informacion.equals("Atrasada")) {
            colorFondo = new Color(204, 204, 255);
        }
        if (informacion.equals("Finalizada")) {
            colorFondo = new Color(153, 255, 102);
        }

        JPanel panelTarea1;
        panelTarea1 = new javax.swing.JPanel();
        panelTarea1.setBackground(colorFondo);
        panelTarea1.setMaximumSize(new java.awt.Dimension(390, 61));
        panelTarea1.setMinimumSize(new java.awt.Dimension(390, 61));
        panelTarea1.setName("" + tarea.getIdTarea());
        panelTarea1.setPreferredSize(new java.awt.Dimension(390, 61));
        panelTarea1.setLayout(new javax.swing.BoxLayout(panelTarea1, javax.swing.BoxLayout.LINE_AXIS));
        agregarEventoClick(panelTarea1);

        JPanel pnlImage;
        pnlImage = new javax.swing.JPanel();
        pnlImage.setBackground(colorFondo);
        pnlImage.setMaximumSize(new java.awt.Dimension(70, 61));
        pnlImage.setMinimumSize(new java.awt.Dimension(70, 61));
        pnlImage.setPreferredSize(new java.awt.Dimension(70, 61));
        panelTarea1.add(pnlImage);

        JLabel lblImage;
        lblImage = new javax.swing.JLabel();
        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon_tasks.png")));
        lblImage.setMaximumSize(new java.awt.Dimension(50, 50));
        lblImage.setMinimumSize(new java.awt.Dimension(50, 50));
        lblImage.setPreferredSize(new java.awt.Dimension(50, 50));
        pnlImage.add(lblImage);
        lblImage.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                Configurations.setImageToLabel(lblImage, "/icon_tasks.png");
            }
        });

        JPanel pnlPrincipal;
        pnlPrincipal = new javax.swing.JPanel();
        pnlPrincipal.setMaximumSize(new java.awt.Dimension(320, 61));
        pnlPrincipal.setMinimumSize(new java.awt.Dimension(320, 61));
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(320, 61));
        pnlPrincipal.setLayout(new javax.swing.BoxLayout(pnlPrincipal, javax.swing.BoxLayout.Y_AXIS));
        panelTarea1.add(pnlPrincipal);

        JPanel pnlTitle;
        pnlTitle = new javax.swing.JPanel();
        pnlTitle.setBackground(colorFondo);
        pnlTitle.setMaximumSize(new java.awt.Dimension(320, 29));
        pnlTitle.setMinimumSize(new java.awt.Dimension(320, 29));
        pnlTitle.setPreferredSize(new java.awt.Dimension(320, 29));
        pnlTitle.setLayout(new java.awt.GridBagLayout());
        pnlPrincipal.add(pnlTitle);

        JLabel lblTitle;
        lblTitle = new javax.swing.JLabel();
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblTitle.setText(tarea.getTitulo());
        lblTitle.setMaximumSize(new java.awt.Dimension(315, 29));
        lblTitle.setMinimumSize(new java.awt.Dimension(315, 29));
        lblTitle.setPreferredSize(new java.awt.Dimension(315, 29));
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        pnlTitle.add(lblTitle, gridBagConstraints);

        JPanel pnlBottom;
        pnlBottom = new javax.swing.JPanel();
        pnlBottom.setBackground(colorFondo);
        pnlBottom.setMaximumSize(new java.awt.Dimension(332, 32));
        pnlBottom.setMinimumSize(new java.awt.Dimension(332, 32));
        pnlBottom.setLayout(new javax.swing.BoxLayout(pnlBottom, javax.swing.BoxLayout.LINE_AXIS));
        pnlPrincipal.add(pnlBottom);

        JPanel pnlDate;
        pnlDate = new javax.swing.JPanel();
        pnlDate.setBackground(colorFondo);
        pnlDate.setMaximumSize(new java.awt.Dimension(231, 28));
        pnlDate.setMinimumSize(new java.awt.Dimension(231, 28));
        pnlDate.setPreferredSize(new java.awt.Dimension(231, 28));
        pnlDate.setLayout(new java.awt.GridBagLayout());
        pnlBottom.add(pnlDate);

        JLabel lblDate;
        lblDate = new javax.swing.JLabel();
        lblDate.setText("Entrega: " + tarea.getFechaEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblDate.setMaximumSize(new java.awt.Dimension(226, 28));
        lblDate.setMinimumSize(new java.awt.Dimension(0, 0));
        lblDate.setPreferredSize(new java.awt.Dimension(226, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        pnlDate.add(lblDate, gridBagConstraints);

        JPanel pnlInfo;
        pnlInfo = new javax.swing.JPanel();
        pnlInfo.setBackground(colorFondo);
        pnlInfo.setMaximumSize(new java.awt.Dimension(89, 32));
        pnlInfo.setMinimumSize(new java.awt.Dimension(89, 32));
        pnlInfo.setLayout(new java.awt.GridBagLayout());
        pnlBottom.add(pnlInfo);

        JLabel lblInfo;
        lblInfo = new javax.swing.JLabel();
        lblInfo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInfo.setText(informacion);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 10, 30);
        pnlInfo.add(lblInfo, gridBagConstraints);

        return panelTarea1;
    }

    private void agregarEventoClick(JPanel panel) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume(); // Evitar múltiples activaciones
                    String id = panel.getName(); // Obtener el name del panel (idTarea)
                    Tarea tarea = _tareaController.getTareaById(Integer.parseInt(id));
                    JFrame ft;
                    if (modo == 1) {
                        ft = new FrameTareaEstudiante(tarea, PanelTareas.this);
                    } else {
                        ft = new FrameTareaDocente(tarea, PanelTareas.this);
                    }
                    ft.setVisible(true);
                }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        panelFondo = new javax.swing.JPanel();
        panelTareas = new javax.swing.JPanel();
        btnNuevaTarea = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(498, 429));
        setMinimumSize(new java.awt.Dimension(498, 429));
        setPreferredSize(new java.awt.Dimension(498, 429));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(498, 429));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(498, 429));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(498, 429));

        panelFondo.setBackground(new java.awt.Color(255, 255, 255));

        panelTareas.setBackground(new java.awt.Color(255, 255, 255));
        panelTareas.setPreferredSize(new java.awt.Dimension(390, 32767));
        panelTareas.setLayout(new javax.swing.BoxLayout(panelTareas, javax.swing.BoxLayout.Y_AXIS));

        btnNuevaTarea.setBackground(new java.awt.Color(51, 102, 0));
        btnNuevaTarea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevaTarea.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaTarea.setText("Nueva Tarea");
        btnNuevaTarea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevaTarea.setBorderPainted(false);
        btnNuevaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(panelTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFondoLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnNuevaTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevaTarea)
                .addGap(18, 18, 18)
                .addComponent(panelTareas, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addGap(34, 34, 34))
        );

        jScrollPane1.setViewportView(panelFondo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTareaActionPerformed
        FrameTareaDocente ft = new FrameTareaDocente(this, idMateria); // Modo nueva tarea
        ft.setVisible(true);
    }//GEN-LAST:event_btnNuevaTareaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaTarea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelTareas;
    // End of variables declaration//GEN-END:variables

}
