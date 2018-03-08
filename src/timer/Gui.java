package timer;
import com.sun.media.sound.JavaSoundAudioClip;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ondřej Mejzlík
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    public Gui() {
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

        startJButton = new javax.swing.JButton();
        amountJTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        startStopJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Timer");

        startJButton.setText("Start");
        startJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startJButtonActionPerformed(evt);
            }
        });

        amountJTextField.setText("0");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Wait minutes:");

        startStopJLabel.setForeground(new java.awt.Color(0, 0, 0));
        startStopJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startStopJLabel.setText("STOPPED");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(amountJTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startStopJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amountJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startStopJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startJButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startJButtonActionPerformed
        //Pokud tlacitko neni zmacknute, timer stoji
        if(!buttonPressed)
        {
            //Pristi akci tlacitka bude resret, nachystej se na to
            buttonPressed = true;
            //Uprav text tlacitka
            startJButton.setText("Reset");
            //Zjisti jak dlouho se bude cekat
            try
            {
                minutes = Integer.parseInt(amountJTextField.getText());
            }
            catch(NumberFormatException ex)
            {
                amountJTextField.setText("Enter a number");
            }   
            //Nastav text jak dlouho se bude cekat
            amountJTextField.setText("Set to: "+minutes+" minutes");
            //Zmen informacni text
            startStopJLabel.setForeground(Color.red);
            startStopJLabel.setText("RUNNING");
            //Spust vlakno s cekanim
            this.timerThreadStart();
        }
        //Tlacitko bylo zmacknuto, timer bezi
        else
        {
            //Dalsi akce tlacitka bude start, priprav se na to
            buttonPressed = false;
            //Resetuj timer
            this.reset();
            //Uprav text tlacitka
            startJButton.setText("Start");
        }
    }//GEN-LAST:event_startJButtonActionPerformed
    
    /**
     * Reset timeru.
     */
    private void reset()
    {
        //Pokud byl nacten zvuk (vyzvani se), vypni ho.
        if(warn1 != null)
        {
            warn1.stop();
        }
        //Nastav vychozi hodnoty
        amountJTextField.setText("0");
        startStopJLabel.setForeground(Color.black);
        startStopJLabel.setText("STOPPED");
        //Zastaveni vlakna timeru
        timer.stop();
    }
    
    /**
     * Zalozi vlakno pro cekani zadany cas a spusti cekani, po cekani zapne vystrazny zvuk.
     */
    public void timerThreadStart()
    {
        //Zaloz nove vlakno a uloz
        timer = new Thread(
            new Runnable() 
            //Anonymni trida noveho vlakna s metodou run        
            {
                @Override
                public void run() 
                {
                    //Cekej zadany cas
                    try 
                    {
                        TimeUnit.MINUTES.sleep(minutes);
                    } 
                    catch (InterruptedException e) 
                    {
                    amountJTextField.setText("Interrupted Exception");
                    }
        
                    //Nacti zvuk
                    try
                    {
                        //Vyrobeni input stream tak, aby sel cist po zabaleni do .jar
                        InputStream is = getClass().getResourceAsStream("warn1.wav");
                        warn1 = new JavaSoundAudioClip(is);
                    }
                    catch(IOException ex)
                    {
                       amountJTextField.setText("IOException");
                    }
                    //Zmen informacni text na "vyznanim"
                    startStopJLabel.setForeground(Color.red);
                    startStopJLabel.setText("RINGING");
                    //Prehraj zvuk
                    warn1.loop();
                }
            });
        //Spust vlakno
        timer.start();
    }
        
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
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               //Vyrob nove okno
               Gui gui = new Gui();
               //Okno nepujde zvetsit nebo zmensit
               gui.setResizable(false);
               //Okno se zobrazi uprostred monitoru
               gui.setLocationRelativeTo(null);
               //Zviditelni hotove okno
               gui.setVisible(true);
            }
        });
    }
    
    //Novy zvukovy klip
    private JavaSoundAudioClip warn1; 
    //Pocet minut k cekani
    private int minutes = 0;
    //Kontrola zmacknuteho tlacitka
    private boolean buttonPressed = false;
    //Promenna pro vlakno timeru
    private Thread timer;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amountJTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton startJButton;
    private javax.swing.JLabel startStopJLabel;
    // End of variables declaration//GEN-END:variables
}
