
package metasearchengine;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.*;
import static java.awt.FlowLayout.CENTER;
import javax.swing.*;

public class MetaSearchEngine extends JFrame{
    
    Integer g=1,y=1,b=1;
    JLabel label;
    JTextField entry;
    JButton OKbutton;
    JButton CancelButton;
    JTextArea textArea;
    static String request;
    String[] FinalLinks = new String[300];
    String theRequest;
    
    
    String userRequest;
    public static String[] YahooLinks = new String[100];
    public static String[] BingLinks = new String[100];
    public static String[] GoogleLinks = new String[100];
    public static String[] BordaLinks = new String[300];
    public static Integer[] BordaScore = new Integer[300];
    
    
    
    
    
    
    public MetaSearchEngine(){

        //this.request = request;
        //setLayout(new GridLayout(2,1));
        
        
        //JPanel enterPanel = new JPanel();
        setLayout(new FlowLayout());
        label = new JLabel("PLease insert your request to search: ");

        entry = new JTextField(50);
        textArea = new JTextArea();
        //textArea.setBounds(0, 200, 600, 200);
        //textArea.setBorder(CENTER);
        textArea.setEditable(false);
        
        
        OKbutton = new JButton("OK");
        CancelButton = new JButton("Cancel");

        event e = new event();
        OKbutton.addActionListener(e); //or this
        CancelButton.addActionListener(e);
        
        JCheckBox Google = new JCheckBox("Google");
        JCheckBox Yahoo = new JCheckBox("Yahoo");
        JCheckBox Bing = new JCheckBox("Bing");
        
        Google.setSelected(true);
        Yahoo.setSelected(true);
        Bing.setSelected(true);
        
        Google.addActionListener(e);
        Yahoo.addActionListener(e);
        Bing.addActionListener(e);

        
        add(label);
        add(entry);
        add(OKbutton);
        add(CancelButton);
        add(Google);
        add(Yahoo);
        add(Bing);
        add(textArea);

    }
    
    public MetaSearchEngine(JTextArea textArea){
        this.textArea = textArea;
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e){
            
            
            String actionCommand = e.getActionCommand();
            
            if(actionCommand.equals("Google")){
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if(selected == false){ g = 0; }
                else if(selected == true){ g = 1;}
            }
            if(actionCommand.equals("Yahoo")){
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if(selected == false){ y = 0; }
                else if(selected == true){ y = 1;}
            }
            if(actionCommand.equals("Bing")){
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if(selected == false){ b = 0; }
                else if(selected == true){ b = 1;}
            }
            
            if(actionCommand.equals("OK")){
               request = entry.getText();
               //MainClass Search = new MainClass();
               //FinalLinks = Search.main(request);
               //MetaSearchEngine La = new MetaSearchEngine();
               //La.add(textArea);
               
               
                String[] YahooLinksKendall = new String[100];
                String[] BingLinksKendall = new String[100];
                String[] GoogleLinksKendall = new String[100];
        
                if(y == 1){
                    Yahoo LinksYahoo = new Yahoo();
                    YahooLinks = LinksYahoo.main(request);
                    for(int i=0; i<100; i++){
                        YahooLinksKendall[i] = YahooLinks[i];
                        //System.out.println("Yahoo Url "+(i+1)+": "+YahooLinks[i]);
                    }
                }
                if(b == 1){
                    Bing LinksBing = new Bing();
                    BingLinks = LinksBing.main(request);
                    for(int i=0; i<100; i++){
                        BingLinksKendall[i] = BingLinks[i];
                       //System.out.println("Bing Url "+(i+1)+": "+BingLinks[i]);  
                    }
                }
                if(g == 1){
                    Google LinksGoogle = new Google();
                    GoogleLinks = LinksGoogle.main(request);
                    for(int i=0; i<100; i++){
                        GoogleLinksKendall[i] = GoogleLinks[i];
                        //System.out.println("Google Url "+(i+1)+": "+GoogleLinks[i]);  
                    }
                }

        
               
        

                




                /*Edw arxizei o Borda~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                String temp = new String();
                String tempCurrentLink = new String();
                Integer tempScore = 0;
                Integer BordaLinksNum = 0;
                Integer score;
                for(int i = 0; i < 300; i++){
                    BordaScore[i] = 0;
                }
                for(int i = 0; i < 100; i++){
                    if(GoogleLinks[i] != null){

                        score = 0;
                        for(int j = 0; j < 100; j++){
                            if(GoogleLinks[i].equals(YahooLinks[j])){
                                //System.out.println("file kati paei strava~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                                for(int k = 0; k < 100; k++){
                                    if(YahooLinks[j].equals(BingLinks[k])){

                                        score = (100-i)+(100-j)+(100-k);
                                        tempCurrentLink = GoogleLinks[i];
                                        for(int w = 0; w < 300; w++){
                                            if(score > BordaScore[w]){

                                                temp = BordaLinks[w];
                                                BordaLinks[w] = tempCurrentLink;
                                                tempCurrentLink = temp;
                                                tempScore = BordaScore[w];
                                                BordaScore[w] = score;
                                                score = tempScore;
                                            }
                                            else if(score == 0 && tempCurrentLink != null){
                                                BordaLinks[BordaLinksNum] = tempCurrentLink;
                                                BordaScore[BordaLinksNum] = 0;
                                                w = 300;
                                            }
                                        }
                                        score = 1;
                                        BordaLinksNum++;
                                        GoogleLinks[i] = null;
                                        YahooLinks[j] = null;
                                        BingLinks[k] = null;
                                        k = 100;
                                    }
                                }
                                j = 100;
                            }
                        }
                        if(score == 0){
                            BordaLinks[BordaLinksNum] = GoogleLinks[i];
                            BordaScore[BordaLinksNum] = 0;
                            BordaLinksNum++;
                        }
                    }
                }

                for(int i = 0; i < 100; i++){
                    if(YahooLinks[i] != null){

                        score = 0;
                        for(int j = 0; j < 100; j++){
                            if(YahooLinks[i].equals(GoogleLinks[j])){
                                //System.out.println("file kati paei strava~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                                for(int k = 0; k < 100; k++){
                                    if(GoogleLinks[j].equals(BingLinks[k])){

                                        score = (100-i)+(100-j)+(100-k);
                                        tempCurrentLink = YahooLinks[i];
                                        for(int w = 0; w < 300; w++){
                                            if(score > BordaScore[w]){

                                                temp = BordaLinks[w];
                                                BordaLinks[w] = tempCurrentLink;
                                                tempCurrentLink = temp;
                                                tempScore = BordaScore[w];
                                                BordaScore[w] = score;
                                                score = tempScore;
                                            }
                                            else if(score == 0 && tempCurrentLink != null){
                                                BordaLinks[BordaLinksNum] = tempCurrentLink;
                                                BordaScore[BordaLinksNum] = 0;
                                                w = 300;
                                            }
                                        }
                                        score = 1;
                                        BordaLinksNum++;
                                        YahooLinks[i] = null;
                                        GoogleLinks[j] = null;
                                        BingLinks[k] = null;
                                        k = 100;
                                    }
                                }
                                j = 100;
                            }
                        }
                        if(score == 0){
                            BordaLinks[BordaLinksNum] = YahooLinks[i];
                            BordaScore[BordaLinksNum] = 0;
                            BordaLinksNum++;
                        }
                    }
                }


                for(int i = 0; i < 100; i++){
                    if(BingLinks[i] != null){

                        score = 0;
                        for(int j = 0; j < 100; j++){
                            if(BingLinks[i].equals(GoogleLinks[j])){
                                //System.out.println("file kati paei strava~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                                for(int k = 0; k < 100; k++){
                                    if(GoogleLinks[j].equals(YahooLinks[k])){

                                        score = (100-i)+(100-j)+(100-k);
                                        tempCurrentLink = BingLinks[i];
                                        for(int w = 0; w < 300; w++){
                                            if(score > BordaScore[w]){

                                                temp = BordaLinks[w];
                                                BordaLinks[w] = tempCurrentLink;
                                                tempCurrentLink = temp;
                                                tempScore = BordaScore[w];
                                                BordaScore[w] = score;
                                                score = tempScore;
                                            }
                                            else if(score == 0 && tempCurrentLink != null){
                                                BordaLinks[BordaLinksNum] = tempCurrentLink;
                                                BordaScore[BordaLinksNum] = 0;
                                                w = 300;
                                            }
                                        }
                                        score = 1;
                                        BordaLinksNum++;
                                        BingLinks[i] = null;
                                        GoogleLinks[j] = null;
                                        YahooLinks[k] = null;
                                        k = 100;
                                    }
                                }
                                j = 100;
                            }
                        }
                        if(score == 0){
                            BordaLinks[BordaLinksNum] = BingLinks[i];
                            BordaScore[BordaLinksNum] = 0;
                            BordaLinksNum++;
                        }
                    }
                }
                /*
                for(int i=0; i<300; i++){
                    System.out.println("Borda Score: "+BordaScore[i]+" Borda Url "+(i+1)+": "+BordaLinks[i]);
                }*/
                /*
                for(int i=0; i<100; i++){
                    System.out.println("Google Url "+i+": "+GoogleLinksKendall[i]);
                }*/
                /*Edw teleiwnei o Borda~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/



                /*Edw arxizei o Kendall~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                float Nc = 0;
                float Nd = 0;

                for(int i = 0; i < 100; i++){
                    for(int k = 0; k < 300; k++){
                        if(GoogleLinksKendall[i].equals(BordaLinks[k])){
                            for(int j = i+1; j < 100; j++){
                                for(int w = 0; w < 300; w++){
                                    if(GoogleLinksKendall[j].equals(BordaLinks[w])){

                                        if(w > k){
                                            Nc++;
                                        }
                                        else if(k > w){
                                            Nd++;
                                        }
                                        w =300;
                                    }
                                }
                            }
                            k =300;
                        }
                    }
                }

                float t;
                float l = Nc - Nd;
                System.out.println("Nc :"+Nc+" Nd :"+Nd+" l :"+l);
                t = l/((100*99)/2);
                System.out.println("Kendall Score for Google :"+t);

                Nc = 0;
                Nd = 0;

                for(int i = 0; i < 100; i++){
                    for(int k = 0; k < 300; k++){
                        if(YahooLinksKendall[i].equals(BordaLinks[k])){
                            for(int j = i+1; j < 100; j++){
                                for(int w = 0; w < 300; w++){
                                    if(YahooLinksKendall[j].equals(BordaLinks[w])){

                                        if(w > k){
                                            Nc++;
                                        }
                                        else if(k > w){
                                            Nd++;
                                        }
                                        w =300;
                                    }
                                }
                            }
                            k =300;
                        }
                    }
                }

                t= 0;
                l = Nc - Nd;
                System.out.println("Nc :"+Nc+" Nd :"+Nd+" l :"+l);
                t = l/((100*99)/2);
                System.out.println("Kendall Score for Yahoo :"+t);

                Nc = 0;
                Nd = 0;

                for(int i = 0; i < 100; i++){
                    for(int k = 0; k < 300; k++){
                        if(BingLinksKendall[i].equals(BordaLinks[k])){
                            for(int j = i+1; j < 100; j++){
                                for(int w = 0; w < 300; w++){
                                    if(BingLinksKendall[j].equals(BordaLinks[w])){

                                        if(w > k){
                                            Nc++;
                                        }
                                        else if(k > w){
                                            Nd++;
                                        }
                                        w =300;
                                    }
                                }
                            }
                            k =300;
                        }
                    }
                }

                t= 0;
                l = Nc - Nd;
                System.out.println("Nc :"+Nc+" Nd :"+Nd+" l :"+l);
                t = l/((100*99)/2);
                System.out.println("Kendall Score for Bing :"+t);
                /*Edw teleiwnei o Kendall~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
               
               
               for(int i = 0; i < 300; i++){
                    textArea.append(BordaLinks[i]+"\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                    //System.out.println(FinalLinks[i]);
               }
               //La.add(textArea);
              
               
            }
            else if(actionCommand.equals("Cancel")){
                entry.setText("");
            }
            //System.out.println(request);
        }
    }
        
        


    public static void main(String[] args) {
        MetaSearchEngine gui_window ;
        
        gui_window = new MetaSearchEngine(); 
        gui_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gui_window.setSize(JFrame.WIDTH,JFrame.HEIGHT);
        gui_window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui_window.setVisible(true);
        gui_window.setResizable(false);
        gui_window.setTitle("MetaSearch_Engine");
        //System.out.println(request);
    }
    
}
