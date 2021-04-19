package myPkg.AlgoGenetico.src;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    static Main thegui;

    JPanel panel0;
    JTextField geracoes_txt;
    JTextArea output_area;
    JButton execCompleto_btn;
    JButton but1;
    JLabel geracoes_btn;
    JButton arquivo_btn;
    JLabel arquivo_lbl;
    JLabel rodadas_btn;
    JTextField rodadas_txt;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }
        thegui = new Main();
    }

    public Main() {
        super("TITLE");

        panel0 = new JPanel();
        GridBagLayout gbpanel0 = new GridBagLayout();
        GridBagConstraints gbcpanel0 = new GridBagConstraints();
        panel0.setLayout(gbpanel0);

        geracoes_txt = new JTextField();
        gbcpanel0.gridx = 15;
        gbcpanel0.gridy = 5;
        gbcpanel0.gridwidth = 10;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(geracoes_txt, gbcpanel0);
        panel0.add(geracoes_txt);

        output_area = new JTextArea(2, 10);
        JScrollPane scroll = new JScrollPane(output_area);
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 19;
        gbcpanel0.gridwidth = 29;
        gbcpanel0.gridheight = 20;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 1;
        gbcpanel0.anchor = GridBagConstraints.SOUTH;
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);    
        gbpanel0.setConstraints(scroll, gbcpanel0);
        panel0.add(scroll);

        execCompleto_btn = new JButton("Execução completa");
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 11;
        gbcpanel0.gridwidth = 14;
        gbcpanel0.gridheight = 3;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(execCompleto_btn, gbcpanel0);
        execCompleto_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_area.setText("");
                Log.priority = 1;
                String geracoes = geracoes_txt.getText();
                if (geracoes != null && !geracoes.isEmpty())
                    Configurations.geracoes = Integer.parseInt(geracoes);

                String rodadas = rodadas_txt.getText();
                if (rodadas != null && !rodadas.isEmpty())
                    Configurations.rodadas = Integer.parseInt(rodadas);
                App.algoritmo();
                output_area.setText(Log.getWhole_log());
                Log.clearLog();
            }
        });
        panel0.add(execCompleto_btn);

        but1 = new JButton("Execução Rápida");
        gbcpanel0.gridx = 18;
        gbcpanel0.gridy = 11;
        gbcpanel0.gridwidth = 13;
        gbcpanel0.gridheight = 3;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(but1, gbcpanel0);
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output_area.setText("");
                Log.priority = 2;
                String geracoes = geracoes_txt.getText();
                if (geracoes != null && !geracoes.isEmpty())
                    Configurations.geracoes = Integer.parseInt(geracoes);
                String rodadas = rodadas_txt.getText();
                if (rodadas != null && !rodadas.isEmpty())
                    Configurations.rodadas = Integer.parseInt(rodadas);
                App.algoritmo();
                output_area.setText(Log.getWhole_log());
                Log.clearLog();
            }
        });
        panel0.add(but1);

        geracoes_btn = new JLabel("Gerações");
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 5;
        gbcpanel0.gridwidth = 9;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 1;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(geracoes_btn, gbcpanel0);
        panel0.add(geracoes_btn);

        arquivo_btn = new JButton("Selecionar Arquivo");
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 8;
        gbcpanel0.gridwidth = 9;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(arquivo_btn, gbcpanel0);
        arquivo_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();

                j.showOpenDialog(null);

                Configurations.NAME_FILE = j.getSelectedFile().getAbsolutePath();
                Configurations.SIZE = new Decoder().read_size(Configurations.NAME_FILE);
                Configurations.INDEX_APTIDAO = Configurations.SIZE;
                System.out.println(Configurations.NAME_FILE);
                System.out.println(Configurations.SIZE);
                System.out.println(Configurations.INDEX_APTIDAO);
                arquivo_lbl.setText(j.getSelectedFile().getName());

            }
        });
        panel0.add(arquivo_btn);

        arquivo_lbl = new JLabel("Nome do Arquivo");
        gbcpanel0.gridx = 15;
        gbcpanel0.gridy = 8;
        gbcpanel0.gridwidth = 16;
        gbcpanel0.gridheight = 2;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 1;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(arquivo_lbl, gbcpanel0);
        panel0.add(arquivo_lbl);

        rodadas_btn = new JLabel("Rodadas");
        gbcpanel0.gridx = 2;
        gbcpanel0.gridy = 1;
        gbcpanel0.gridwidth = 9;
        gbcpanel0.gridheight = 3;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 1;
        gbcpanel0.anchor = GridBagConstraints.NORTHWEST;
        gbpanel0.setConstraints(rodadas_btn, gbcpanel0);
        panel0.add(rodadas_btn);

        rodadas_txt = new JTextField();
        rodadas_txt.setMaximumSize(new Dimension(100,100));
        gbcpanel0.gridx = 15;
        gbcpanel0.gridy = 1;
        gbcpanel0.gridwidth = 16;
        gbcpanel0.gridheight = 3;
        gbcpanel0.fill = GridBagConstraints.BOTH;
        gbcpanel0.weightx = 1;
        gbcpanel0.weighty = 0;
        gbcpanel0.anchor = GridBagConstraints.NORTH;
        gbpanel0.setConstraints(rodadas_txt, gbcpanel0);
        panel0.add(rodadas_txt);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(panel0);
        pack();
        setVisible(true);
    }
}
