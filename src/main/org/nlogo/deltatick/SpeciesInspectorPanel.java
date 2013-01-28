package org.nlogo.deltatick;

import org.nlogo.deltatick.dialogs.TraitSelector;
import org.nlogo.deltatick.xml.Trait;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: aditiwagh
 * Date: 1/20/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpeciesInspectorPanel extends JPanel {
    //JPanel contentPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    BreedBlock myParent;
    JLabel breedNameLabel = new JLabel("Species");
    JLabel breedName = new JLabel();
    JLabel lifeSpanLabel = new JLabel("Age");
    JLabel startLifeSpan = new JLabel("From");
    JLabel endLifeSpan = new JLabel("to");
    JTextField startLifeSpanBlank = new JTextField();
    JTextField endLifeSpanBlank = new JTextField();
    JLabel energyLabel = new JLabel("Energy");
    JLabel lowestEnergy = new JLabel("lowest");
    JLabel highestEnergy = new JLabel("highest");
    JLabel lowestEnergyValue = new JLabel("0");
    JTextField highestEnergyBlank = new JTextField();
    JButton okayButton = new JButton("Okay"); // placeholder button to test sending values - A. (Jan 22, 2013)
    JButton addTrait = new JButton("add trait");

    JFrame myFrame;

    JTabbedPane traitsTabbedPane;
    TraitSelector traitSelector;
    int countTabs = 1;

    SpeciesInspector speciesInspector = new SpeciesInspector();

    public SpeciesInspectorPanel(BreedBlock myParent, JFrame myFrame) {
        this.myParent = myParent;
        this.myFrame = myFrame;
        breedName.setText(myParent.plural());
        highestEnergyBlank.setText(myParent.breed.getOwnVarMaxReporter("energy"));
        endLifeSpanBlank.setText(myParent.breed.getOwnVarMaxReporter("age"));
        activateButtons();
    }

    public String getEndListSpan() {
        return endLifeSpanBlank.getText().toString();
    }

    public String getHighestEnergy() {
        return highestEnergyBlank.getText().toString();
    }

    public void addPanels(Container pane) {
        topPanel.setBackground(ColorSchemer.getColor(3));
        bottomPanel.setBackground(ColorSchemer.getColor(2));
        TitledBorder titleTopPanel;
        titleTopPanel = BorderFactory.createTitledBorder("Set up");
        topPanel.setBorder(titleTopPanel);
        setupTopPanel();
        TitledBorder titleBottomPanel;
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        titleBottomPanel = BorderFactory.createTitledBorder(loweredetched, "Traits");

        bottomPanel.setBorder(titleBottomPanel);
        setupBottomPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(topPanel);
        pane.add(bottomPanel);

    }

    public void setupTopPanel() {
        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(breedNameLabel)
                    .addComponent(breedName))
                .addGroup(layout.createParallelGroup()
                    .addComponent(lifeSpanLabel)
                    .addComponent(startLifeSpan)
                    .addComponent(startLifeSpanBlank)
                    .addComponent(endLifeSpan)
                    .addComponent(endLifeSpanBlank))
                .addGroup(layout.createParallelGroup()
                    .addComponent(energyLabel)
                    .addComponent(lowestEnergy)
                    .addComponent(lowestEnergyValue)
                    .addComponent(highestEnergy)
                    .addComponent(highestEnergyBlank))
                .addComponent(okayButton)
        );

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(breedNameLabel)
                        .addComponent(lifeSpanLabel)
                        .addComponent(energyLabel))
                .addGroup(layout.createParallelGroup()
                .addComponent(breedName)
                .addGroup(layout.createSequentialGroup()
                .addComponent(startLifeSpan)
                .addComponent(startLifeSpanBlank)
                .addComponent(endLifeSpan)
                .addComponent(endLifeSpanBlank))
                .addGroup(layout.createSequentialGroup()
                .addComponent(lowestEnergy)
                .addComponent(lowestEnergyValue)
                .addComponent(highestEnergy)
                .addComponent(highestEnergyBlank)))
                .addComponent(okayButton)
        );
        validate();
    }

    public void setupBottomPanel() {
        GroupLayout layout = new GroupLayout(bottomPanel);
        bottomPanel.setLayout(layout);
        traitsTabbedPane = new JTabbedPane();

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addComponent(addTrait)
                .addComponent(traitsTabbedPane)
        );

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                .addComponent(addTrait)
                .addComponent(traitsTabbedPane)
        );
        validate();

        if (countTabs == 1) {
            JPanel panel1 = new JPanel();
        }
    }

    public void populateTraitTabs() {
        for (Trait trait : speciesInspector.getSelectedTraitsList()) {
            TraitDisplayPanel traitDisplayPanel = new TraitDisplayPanel(trait);
        }
    }

    public void activateButtons() {
        addTrait.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                traitSelector = new TraitSelector();
                traitSelector.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                traitSelector.setTraits(myParent.getTraits());
                traitSelector.showMe();
                speciesInspector.selectedTraitsList.add(traitSelector.getSelectedTrait());
                TraitDisplayPanel traitDisplayPanel = new TraitDisplayPanel(traitSelector.getSelectedTrait());
                traitDisplayPanel.setPreferredSize(new Dimension(500,150));
                traitsTabbedPane.addTab(traitSelector.getSelectedTrait().getNameTrait(), traitDisplayPanel);
            }
        });
    }

    public SpeciesInspector getSpeciesInspector() {
        return speciesInspector;
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JFrame getMyFrame() {
        return myFrame;
    }

    public BreedBlock getMyParent() {
        return myParent;
    }
}
