/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Options.java
 *
 * Created on Dec 11, 2011, 7:19:40 PM
 */
package View;

import Components.Difficulty;
import Model.Game;
import javax.swing.ButtonGroup;

/**
 *
 * @author Laurens
 */
public class Options extends javax.swing.JPanel {

	private Game game;
	
	/** Creates new form Options */
	public Options(Game game) {
		this.game = game;
		initComponents();
		setButtonGroups();
		initScreen();
	}
	
	private void setButtonGroups() {
		ButtonGroup boxPercentage = new ButtonGroup();
		boxPercentage.add(btnBox15);
		boxPercentage.add(btnBox20);
		boxPercentage.add(btnBox25);
		
		ButtonGroup rockPercentage = new ButtonGroup();
		rockPercentage.add(btnRock10);
		rockPercentage.add(btnRock15);
		rockPercentage.add(btnRock20);
		
		ButtonGroup difficulty = new ButtonGroup();
		difficulty.add(btnEasy);
		difficulty.add(btnMedium);
		difficulty.add(btnHard);
	}
	
	private void initScreen() {
		lblDimension.setText(Integer.toString(game.getPlayfieldDimension()));
		sliderDimension.setValue(game.getPlayfieldDimension());
		
		if (game.getDifficulty() == Difficulty.EASY) {
			btnEasy.setSelected(true);
		} else if (game.getDifficulty() == Difficulty.MEDIUM) {
			btnMedium.setSelected(true);
		} else if (game.getDifficulty() == Difficulty.HARD) {
			btnHard.setSelected(true);
		}
		
		if (game.getBoxPercentage() == 25) {
			btnBox25.setSelected(true);
		} else if (game.getBoxPercentage() == 20) {
			btnBox20.setSelected(true);
		} else {
			btnBox15.setSelected(true);
		}
		
		if (game.getRockPercentage() == 20) {
			btnRock20.setSelected(true);
		} else if (game.getRockPercentage() == 15) {
			btnRock15.setSelected(true);
		} else { 
			btnRock10.setSelected(true);
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

        btnRock10 = new javax.swing.JRadioButton();
        btnRock15 = new javax.swing.JRadioButton();
        btnBox20 = new javax.swing.JRadioButton();
        btnBox25 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        btnBox15 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        btnRock20 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEasy = new javax.swing.JRadioButton();
        btnMedium = new javax.swing.JRadioButton();
        btnHard = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        sliderDimension = new javax.swing.JSlider();
        lblDimension = new javax.swing.JLabel();

        btnRock10.setText("10%");

        btnRock15.setText("15%");

        btnBox20.setText("20%");

        btnBox25.setText("25%");

        jLabel3.setText("Rocks:");

        btnBox15.setText("15%");

        jLabel2.setText("Boxes:");

        btnRock20.setText("20%");

        jLabel1.setText("Difficulty:");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setText("Save and play");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEasy.setText("Easy");

        btnMedium.setText("Medium");

        btnHard.setText("Hard");

        jLabel4.setText("Playfield dimension:");

        sliderDimension.setMaximum(30);
        sliderDimension.setMinimum(10);
        sliderDimension.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderDimensionStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDimension, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRock10)
                            .addComponent(btnBox15)
                            .addComponent(btnEasy))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMedium)
                            .addComponent(btnBox20)
                            .addComponent(btnRock15))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHard)
                            .addComponent(btnBox25)
                            .addComponent(btnRock20)))
                    .addComponent(sliderDimension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnSave)
                        .addGap(28, 28, 28)
                        .addComponent(btnCancel)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblDimension, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderDimension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEasy)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHard)
                        .addComponent(btnMedium)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBox15)
                    .addComponent(btnBox25)
                    .addComponent(btnBox20))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(7, 7, 7)
                        .addComponent(btnRock10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRock20)
                            .addComponent(btnRock15))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		game.showPlayfieldPanel();
	}//GEN-LAST:event_btnCancelActionPerformed

	private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
		int boxPercentage = btnBox15.isSelected() ? 15 : btnBox20.isSelected() ? 20 : 25;
		int rockPercentage = btnRock10.isSelected() ? 10 : btnRock15.isSelected() ? 15 : 20;
		Difficulty difficulty = btnEasy.isSelected() ? Difficulty.EASY : btnMedium.isSelected() ? Difficulty.MEDIUM : Difficulty.HARD;
		
		game.setPlayfieldDimension(sliderDimension.getValue());
		game.setBoxPercentage(boxPercentage);
		game.setRockPercentage(rockPercentage);
		game.setDifficulty(difficulty);
		
		game.reset();
	}//GEN-LAST:event_btnSaveActionPerformed

	private void sliderDimensionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderDimensionStateChanged
		lblDimension.setText(Integer.toString(sliderDimension.getValue()));
	}//GEN-LAST:event_sliderDimensionStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnBox15;
    private javax.swing.JRadioButton btnBox20;
    private javax.swing.JRadioButton btnBox25;
    private javax.swing.JButton btnCancel;
    private javax.swing.JRadioButton btnEasy;
    private javax.swing.JRadioButton btnHard;
    private javax.swing.JRadioButton btnMedium;
    private javax.swing.JRadioButton btnRock10;
    private javax.swing.JRadioButton btnRock15;
    private javax.swing.JRadioButton btnRock20;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblDimension;
    private javax.swing.JSlider sliderDimension;
    // End of variables declaration//GEN-END:variables
}
