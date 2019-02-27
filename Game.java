import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Game extends JFrame{
   
   JPanel tiles;
   JPanel top;
   private JTextField number = new JTextField(24);
   int check = -1;
   int x = 0;
   int p = 7;
   int q = 6;
   int r = 5;
   int t = 1;
   JButton[] a ;
   ArrayList<Integer> sideburn = new ArrayList();
   ArrayList<Integer> storage = new ArrayList();
   String stringadd = "";
   int TotalScore = 0;
   int score = 0;
   char[] LETTER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T','U', 'V', 'W', 'X', 'Y','Z'};
   JLabel scores;
   TileCollection s;
   theHandler handle;
   JMenuItem reset;
   JMenuItem six;
   JMenuItem eight;
   JButton b;
   JMenuItem help;
   JMenuItem About;
   int tilevalue = 0;
   Color[] colours = {Color.CYAN,Color.RED,Color.GREEN,Color.BLUE,Color.MAGENTA,Color.ORANGE,Color.PINK};
   int choose = 0;
   
   public Game(){	// first 3 lines are the usual way to start the JFrame (window) construction:
   	setTitle("WORD GAME");
   	setSize(400, 400);
   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JMenu menu = new JMenu("Menu");
      handle = new theHandler();
      reset = new JMenuItem("New Game");
      reset.addActionListener(handle);
      menu.add(reset);
      
      help = new JMenuItem("Help");
      help.addActionListener(handle);
      menu.add(help);
      
      six = new JMenuItem("6x6 size");
      six.addActionListener(handle);
      menu.add(six);
      
      eight = new JMenuItem("8x8 size");
      eight.addActionListener(handle);
      menu.add(eight);
      
      About = new JMenuItem("About");
      About.addActionListener(handle);
      menu.add(About);
      
      JMenuBar bar = new JMenuBar( );
      bar.add(menu);
      setJMenuBar(bar);
      
      top = new JPanel();
	   tiles = new JPanel();
	   setLayout(new BorderLayout() );
	   add(top, BorderLayout.NORTH);
	   add(tiles, BorderLayout.CENTER);
      //int score = 0;
      
      top.setLayout( new FlowLayout() );
      // JButton reset = new JButton();
//       reset.setText("New Game" );
//       menu.add(reset);
//       reset.addActionListener(handle);
      b = new JButton();
      b.setText("Done:" );
      top.add(b);
      b.addActionListener(handle);
      scores = new JLabel("Score: "+score);
      top.add(scores);
      tiles.setLayout( new GridLayout(6,6) );
   
      s = new TileCollection();
      
      a = new JButton[36];
      tiles.setLayout( new GridLayout(6,6) );
      
      for (int j = 0; j < a.length; j++){
         a[j] = new TileGUI(s.removeOne());
         a[j].setForeground(Color.yellow);
         a[j].setBackground(Color.LIGHT_GRAY);
         tiles.add(a[j]);
         a[j].addActionListener(handle);
      }
   }
   
   private class theHandler implements ActionListener{
      public void actionPerformed(ActionEvent press){
         if(check == -1){
            for (int j = 0; j < a.length; j++){
               if(press.getSource() == a[j]){
                  if(a[j].getBackground() == Color.LIGHT_GRAY){   
                     a[j].setBackground(colours[choose]);
                     check ++;
                     sideburn.add(j);
                     storage.add(j);
                     x = j;
                     
                     String text = press.getActionCommand().trim();
                     
                     int position = Integer.parseInt(text.substring(51,text.length()-6));
                     
                     TotalScore += position;
                     System.out.println(TotalScore);
                     for(char i: LETTER){
                        if(text.indexOf(i) !=-1){
                           stringadd = stringadd + i;
                           System.out.println(stringadd);
                        }
                     }
                  }
               }
            }
         }
         
         else{
            for (int j = 0; j < a.length; j++){
               if(press.getSource() == a[j]){
                  if(storage.contains(j)&& x!=j){
                     JOptionPane.showMessageDialog(null, "Letter has already been used","REPEATING ERROR",JOptionPane.PLAIN_MESSAGE);
                  }
                  else{
                     if((j == (x-p)) ||(j == (x-q)) ||(j == (x-r)) ||(j == (x-t)) ||(j == (x+t)) ||(j == (x+r)) ||(j == (x+q)) ||(j == (x+p)) ||(j==x) ){                        if(a[j].getBackground() == Color.LIGHT_GRAY){   
                           a[j].setBackground(colours[choose]);
                           check ++;
                           sideburn.add(0, j);
                           storage.add(0, j);
                           x = j;
                           String text = press.getActionCommand().trim();
                        
                           int position = Integer.parseInt(text.substring(51,text.length()-6));
                           
                           TotalScore += position;
                           System.out.println(TotalScore);
                           for(char i: LETTER){
                              if(text.indexOf(i) !=-1){
                                 stringadd = stringadd + i;
                                 System.out.println(stringadd);
                              }
                           }   
                        }
                        else{  
                           a[j].setBackground(Color.LIGHT_GRAY);
                           check --;
                           sideburn.remove(0);
                           storage.remove(0);
                           if(sideburn.size() != 0){
                           x = sideburn.get(0);}
                           String text = press.getActionCommand().trim();
                        
                           int position = Integer.parseInt(text.substring(51,text.length()-6));
                           
                           TotalScore -= position;
                           System.out.println(TotalScore);
                           stringadd = stringadd.substring(0,stringadd.length()-1);
                           System.out.println(stringadd);
                        }
                  }
               }
            }
         }
      }
      if(press.getSource() == b){
         
         try {
            String[] results = FileToArray.read("EnglishWords.txt");
            for (String word : results) {
               if (stringadd.toLowerCase().equals(word)){
                sideburn.clear();
                check = -1;
                stringadd = "";
                System.out.println(word);
                score = score + TotalScore;
                scores.setText("Score: "+ score);
                TotalScore = 0;
                System.out.println("the score is "+score);
                if(choose == 6){choose = 0;}
                else{choose++;}
                break;
               }
            }
            if( check != -1){
               int att;
               //JOptionPane.showMessageDialog(null, "Word not in dictionary");
               for (int j = 0; j <= check; j++){
                  att = sideburn.get(0);
                  a[att].setBackground(Color.LIGHT_GRAY);
                  sideburn.remove(0);
                  storage.remove(0);
               }
                //sideburn.clear();
                check = -1;
                TotalScore = 0;
                stringadd = "";
                System.out.println(" nope" );
            }   
         }
         catch (Exception exception) {
            System.out.println("Whoops, file error:");
            System.out.println(exception);
         }
      }
      if(press.getSource() == reset ){
         s = new TileCollection();
         //a = new JButton[36];
         JButton button;
         
         for (int j = 0; j < a.length; j++){
            button = (JButton) a[j];
            tiles.remove(button);
                tiles.revalidate();
                tiles.repaint();
         }
         for (int j = 0; j < a.length; j++){
            a[j] = new TileGUI(s.removeOne());
            a[j].setForeground(Color.yellow);
            a[j].setBackground(Color.LIGHT_GRAY);
            tiles.add(a[j]);
            a[j].addActionListener(handle);}
         check = -1;
         stringadd = "";
         sideburn.clear();
         storage.clear();
         score = 0;
         TotalScore = 0;
         scores.setText("Score: "+ score);
      }
      if(press.getSource() == help ){
         JOptionPane.showMessageDialog(null, "Rules:\n1.Words may only be formed from sequences of adjacent tiles.\n2.Two tiles are adjacent if their edges or corners meet.\n3.A tile may be used in at most one word.","RULES",JOptionPane.PLAIN_MESSAGE);
      }
      if(press.getSource() == About ){
         JOptionPane.showMessageDialog(null, "WORDGAME\nVersion2.0\nMain Programmer:\nNkuna PTM\nContributors:\nMbele LM\nNkabinde MM","ABOUT",JOptionPane.PLAIN_MESSAGE);
      }
      if(press.getSource() == eight ){    //when user clicks for an 8x8 matrix
         s = new TileCollection();
         //a = new JButton[36];
         JButton button;
         
         for (int j = 0; j < a.length; j++){
            button = (JButton) a[j];
            tiles.remove(button);
                tiles.revalidate();
                tiles.repaint();
         }
         a = new JButton[64];
         tiles.setLayout( new GridLayout(8,8) );

         for (int j = 0; j < a.length; j++){
            a[j] = new TileGUI(s.removeOne());
            a[j].setForeground(Color.yellow);
            a[j].setBackground(Color.LIGHT_GRAY);
            tiles.add(a[j]);
            a[j].addActionListener(handle);}
         check = -1;
         stringadd = "";
         sideburn.clear();
         storage.clear();
         score = 0;
         //p1score = 0;
         //p2score = 0;
         TotalScore = 0;
         //if(state == 0){
         scores.setText("Score: "+ score);
         //else{scores.setText("P1-Score: "+ p1score+" "+"P2-Score: " + p2score);}
         setSize(600, 600);
         p=9;q=8;r=7;
      }
      
      if(press.getSource() == six ){      //when user wants a 6x6 game
         s = new TileCollection();
         //a = new JButton[36];
         JButton button;
         
         for (int j = 0; j < a.length; j++){
            button = (JButton) a[j];
            tiles.remove(button);
                tiles.revalidate();
                tiles.repaint();
         }
         a = new JButton[36];
         tiles.setLayout( new GridLayout(6,6) );

         for (int j = 0; j < a.length; j++){
            a[j] = new TileGUI(s.removeOne());
            a[j].setForeground(Color.yellow);
            a[j].setBackground(Color.LIGHT_GRAY);
            tiles.add(a[j]);
            a[j].addActionListener(handle);}
         check = -1;
         stringadd = "";
         sideburn.clear();
         storage.clear();
         score = 0;
         //p1score = 0;
         //p2score = 0;
         TotalScore = 0;
         //if(state == 0){
         scores.setText("Score: "+ score);
         //else{scores.setText("P1-Score: "+ p1score+" "+"P2-Score: " + p2score);}
         setSize(400, 400);
         p=7;q=6;r=5;
      }
   }
}
}