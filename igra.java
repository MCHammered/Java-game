import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;



public class igra {
    private JFrame frame;
    private JPanel panel;

    private ButtonId[] buttons = new ButtonId[9];
    private ArrayList<ButtonId> arr = new ArrayList<ButtonId>(); // tekom igre narasca

    private int step;

    private Color[] colors = {
        new Color(255, 99, 71),
        new Color(255, 165, 0),
        new Color(255, 223, 186),
        new Color(200, 99, 71),
        new Color(255, 70, 0),
        new Color(255, 215, 0),
        new Color(245, 222, 179),
        new Color(139, 69, 19),
        new Color(210, 105, 30)
    };

    // konstruktor
    public igra(){

        // Postavimo okolje
        frame = new JFrame();

        frame.setTitle("Test igre"); // naslov okna
        frame.setSize(600, 600); // velikost okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // zapre aplikacijo ko uporabnik zakljuci
        frame.setLocationRelativeTo(null); // okno se odpre na sredini ekrana

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        // postavimo gumbe
        for(int i=0; i<9; i++){
            JButton button = new JButton();
            buttons[i] = new ButtonId(button, i, colors[i]);
            button.addActionListener(buttons[i]);
            panel.add(button); // dodajanje gumba v grid
        }

        frame.add(panel);
        frame.setVisible(true);

        // pricnemo z igro  
        playGame();
    }

    private class ButtonId implements ActionListener {
        private JButton button;
        private int id;
        private Color color;

        public ButtonId(JButton button, int id, Color color){
            this.button = button;
            this.id = id;
            this.color = color;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            /* Preverimo ce se i-ta akcija ujema z i-tim
            elementom v zaporedju arr */

            System.out.println("Button " + id + " was pressed");

            button.setBackground(color);

            // ponastavimo barvo gumba 
            Timer timer = new Timer(500, new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent evt) {
                    button.setBackground(null);
                    
                    ((Timer) evt.getSource()).stop();
                }
            });

            timer.setRepeats(false);  // timer se pozene enkrat
            timer.start();

            // ujemanje
            if(id != arr.get(step).id){
                JOptionPane.showMessageDialog(null, "Game over. Your score: " + (arr.size()-1));
                resetGame();
            }
            
            else if(step == arr.size()-1){
                playGame();
            }
            else {step++;}
            
        }
    }


    private void addElement(){
        Random random = new Random();
        Integer randomInt = random.nextInt(9);
        arr.add(buttons[randomInt]);
    }

    private void displaySequence(){
            
        // po 500 milisekund pozenemo akcijo
        Timer timer = new Timer(1000, new ActionListener() {
            private int index = 0; 

            @Override
            public void actionPerformed(ActionEvent e) {
                // ponastavimo barvo gumba v (i-1)-tem zaporedju
                if (index > 0) {
                    arr.get(index-1).button.setBackground(null);
                }
                // spremenimo barvo gumba v i-tem zaporedju
                else if (index < arr.size()) {
                    Color colr = arr.get(index).color;
                    arr.get(index).button.setBackground(colr);
                    index++;
                } else {
                    // zakljucimo z akcijo, ko so vsi gumbi v zaporedju prikazani
                    ((Timer) e.getSource()).stop();

                    for(int i=0; i<9; i++){
                        buttons[i].button.setEnabled(true);
                    }
                }
            }
        }); 

        timer.start();



    }

    private void playGame(){
        step = 0;
        for(int i=0; i<9; i++){
            buttons[i].button.setEnabled(false);
        }

        //vnesemo nov element v zaporedje
        addElement();

        //prikazemo shranjeno zaporedje
        displaySequence();


    }

    private void resetGame(){
        arr.clear();
        wait(1000);
        playGame();
    }

    private void wait(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    } 

    public static void main(String[] args) {
        new igra();
    }
}
