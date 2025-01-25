package Models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Configurations {

    /**
     * Método para ajustar una imagen al tamaño de un JLabel.
     *
     * @param label El JLabel donde se mostrará la imagen.
     * @param imagePath La ruta de la imagen (relativa al classpath).
     */
    public static void setImageToLabel(JLabel label, String imagePath) {
        try {
            // Cargar la imagen
            ImageIcon originalIcon = new ImageIcon(Configurations.class.getResource(imagePath));
            // Redimensionar la imagen al tamaño del JLabel
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH
            );
            // Establecer el icono redimensionado en el JLabel
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            // En caso de error, mostrar el mensaje
            System.err.println("No se pudo cargar la imagen: " + imagePath);
            e.printStackTrace();
        }
    }

    /**
     * Método para agregar un placeholder a un JTextField.
     *
     * @param textField El JTextField donde se aplicará el placeholder.
     * @param placeholder El texto del placeholder.
     */
    public static void addPlaceholder(JTextField textField, String placeholder) {
        // Color del placeholder (gris claro)
        Color placeholderColor = Color.GRAY;

        // Guardar el color original del texto
        Color originalColor = textField.getForeground();

        // Variable interna para rastrear si el texto actual es el placeholder
        final boolean[] isPlaceholder = {true};

        // Configurar el texto inicial del placeholder
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);

        // Agregar un FocusListener para manejar eventos de enfoque
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Eliminar el placeholder solo si está presente
                if (isPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(originalColor);
                    isPlaceholder[0] = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restaurar el placeholder solo si el campo está vacío
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                    isPlaceholder[0] = true;
                }
            }
        });
    }

    public static void addPlaceholder(JTextArea textArea, String placeholder) {
        // Color del placeholder (gris claro)
        Color placeholderColor = Color.GRAY;

        // Guardar el color original del texto
        Color originalColor = textArea.getForeground();

        // Variable interna para rastrear si el texto actual es el placeholder
        final boolean[] isPlaceholder = {true};

        // Configurar el texto inicial del placeholder
        textArea.setText(placeholder);
        textArea.setForeground(placeholderColor);

        // Agregar un FocusListener para manejar eventos de enfoque
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Eliminar el placeholder solo si está presente
                if (isPlaceholder[0]) {
                    textArea.setText("");
                    textArea.setForeground(originalColor);
                    isPlaceholder[0] = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restaurar el placeholder solo si el campo está vacío
                if (textArea.getText().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(placeholderColor);
                    isPlaceholder[0] = true;
                }
            }
        });
    }

    /**
     * Método para añadir un evento hover a un JLabel con una imagen. Oscurece
     * la imagen al pasar el mouse y la restaura al salir.
     *
     * @param label El JLabel al que se le aplicará el efecto hover.
     * @param imagePath La ruta de la imagen (relativa al classpath).
     */
    public static void addHoverEffectToLabel(JLabel label, String imagePath) {
        try {
            // Cargar la imagen original
            ImageIcon originalIcon = new ImageIcon(Configurations.class.getResource(imagePath));
            BufferedImage originalImage = toBufferedImage(originalIcon.getImage());

            // Crear una versión más oscura de la imagen
            BufferedImage darkerImage = applyDarkerFilter(originalImage);

            // Asegurar que ambas imágenes estén redimensionadas al tamaño del JLabel
            SwingUtilities.invokeLater(() -> {
                int width = label.getWidth();
                int height = label.getHeight();
                ImageIcon resizedOriginalIcon = new ImageIcon(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                ImageIcon resizedDarkerIcon = new ImageIcon(darkerImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));

                // Establecer imagen inicial redimensionada
                label.setIcon(resizedOriginalIcon);

                // Añadir eventos de hover
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // Cambiar a la imagen oscurecida al pasar el mouse
                        label.setIcon(resizedDarkerIcon);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // Restaurar la imagen original al salir del mouse
                        label.setIcon(resizedOriginalIcon);
                    }
                });
            });
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen para hover: " + imagePath);
            e.printStackTrace();
        }
    }

    /**
     * Convierte una Image a BufferedImage para manipular píxeles.
     *
     * @param img La imagen a convertir.
     * @return La imagen como BufferedImage.
     */
    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bImage;
    }

    /**
     * Aplica un filtro oscuro a una imagen.
     *
     * @param img La imagen original.
     * @return Una imagen más oscura.
     */
    private static BufferedImage applyDarkerFilter(BufferedImage img) {
        BufferedImage darkerImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgba = img.getRGB(x, y);
                Color color = new Color(rgba, true);

                // Oscurecer el color reduciendo el brillo
                int red = Math.max((int) (color.getRed() * 0.7), 0);
                int green = Math.max((int) (color.getGreen() * 0.7), 0);
                int blue = Math.max((int) (color.getBlue() * 0.7), 0);

                // Crear el nuevo color oscuro
                Color darkerColor = new Color(red, green, blue, color.getAlpha());
                darkerImage.setRGB(x, y, darkerColor.getRGB());
            }
        }
        return darkerImage;
    }
    
    public static void setApplicationIcon(JFrame frame) {   
        String iconPath = "/stm_logo.png";
        try {
            frame.setIconImage(new ImageIcon(frame.getClass().getResource(iconPath)).getImage());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el ícono: " + iconPath);
            e.printStackTrace();
        }
    }

}
