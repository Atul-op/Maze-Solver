package mazesolver;

/**
 *
 * @author Atul Gupta
 */
import java.awt.*;
import static java.awt.BorderLayout.WEST;
import javax.swing.*;
import java.util.*;
import javax.swing.border.TitledBorder;


public class MazeSolver {
    static final int rows = 50;
    static final int cols = 50;
    public static int[][] maze = new int[rows][cols];

    static int mode = 1; // 1 = Obstacle, 2 = Start, 3 = End
    static Point start = new Point(-1,-1);
    static Point end = new Point(-1,-1);
    static int count;
    static String shortest;

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Solver");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 900);
            frame.setLayout(new BorderLayout());
            frame.setBackground(Color.DARK_GRAY);

            JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
            JButton[][] buttons = new JButton[rows][cols];
            gridPanel.setBackground(Color.DARK_GRAY);
            
            // Create maze grid
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    JButton btn = new JButton();
                    btn.setBackground(Color.WHITE);
                    final int x = i, y = j;
                    btn.addActionListener(e -> {
                        if (mode == 1) { // Obstacle toggle
                            if (maze[x][y] == 1) {
                                btn.setBackground(Color.WHITE);
                                maze[x][y] = 0;
                            } else {
                                btn.setBackground(Color.BLACK);
                                maze[x][y] = 1;
                            }
                        } else if (mode == 2) { // Set Start
                            if (start.x != -1 && start.y != -1)
                            {
                                buttons[start.y][start.x].setBackground(Color.WHITE);
                                maze[start.y][start.x] = 0;
                            }
                            btn.setBackground(new Color(0,102,0));
                            maze[x][y] = 2;
                            start.y = x;
                            start.x = y;
                        } else if (mode == 3) { // Set End
                            if (end.x != -1 && end.y != -1)
                            {
                                buttons[end.y][end.x].setBackground(Color.WHITE);
                                maze[end.y][end.x] = 0;
                            }
                            btn.setBackground(new Color(204,0,0));
                            maze[x][y] = 3;
                            end.y = x;
                            end.x = y;
                        }
                    });
                    buttons[i][j] = btn;
                    gridPanel.add(btn);
                }
            }

            // Control panel
            JPanel controlPanel = new JPanel();
            
            
            JButton clearBtn = new JButton("Clear Maze");
            
            JButton fillBtn = new JButton("Fill Maze");
            
            
            JToggleButton obstacleBtn = new JToggleButton("Obstacle", true);
            JToggleButton startBtn = new JToggleButton("Start");
            JToggleButton endBtn = new JToggleButton("End");
            
            
            ButtonGroup group = new ButtonGroup();
            group.add(obstacleBtn);
            group.add(startBtn);
            group.add(endBtn);

            obstacleBtn.addActionListener(e -> mode = 1);
            startBtn.addActionListener(e -> mode = 2);
            endBtn.addActionListener(e -> mode = 3);

            controlPanel.add(obstacleBtn,WEST);
            controlPanel.add(startBtn);
            controlPanel.add(endBtn);
            
            controlPanel.add(Box.createHorizontalStrut(80));
            
            controlPanel.add(clearBtn);
            controlPanel.add(fillBtn);
            
            controlPanel.setBackground(Color.DARK_GRAY);

            // Clear button
            clearBtn.addActionListener(e -> {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        maze[i][j] = 0;
                        buttons[i][j].setBackground(Color.WHITE);
                    }
                }
                start.x = start.y = end.x = end.y = -1;
            });
            
            // Fill button
            fillBtn.addActionListener(e -> {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        maze[i][j] = 1;
                        buttons[i][j].setBackground(Color.BLACK);
                    }
                }
                start.x = start.y = end.x = end.y = -1;
            });
            
            //Panel to display solve button
            JPanel solveButtonPanel = new JPanel(new BorderLayout());
            JButton solveBtn = new JButton("SOLVE");
            solveButtonPanel.add(solveBtn);
            
            // Label for integer result
            JLabel intResultLabel = new JLabel("Number of Ways : ");
            intResultLabel.setForeground(Color.WHITE);

            // Text area for displaying all paths
            JTextArea pathList = new JTextArea(3, 20);
            pathList.setEditable(false);
            pathList.setForeground(Color.WHITE);
            pathList.setBackground(Color.DARK_GRAY);
            JScrollPane scrollPane1 = new JScrollPane(pathList);
            
            // Text area for displaying shortest path
            JTextArea shortestPath = new JTextArea(2, 20);
            shortestPath.setEditable(false);
            shortestPath.setForeground(Color.WHITE);
            shortestPath.setBackground(Color.DARK_GRAY);
            JScrollPane scrollPane2 = new JScrollPane(shortestPath);
            
            
            // Panel to display integer and ArrayList<String> result
            JPanel resultPanel = new JPanel(new BorderLayout());
            TitledBorder border = BorderFactory.createTitledBorder("Result Panel");
            border.setTitleColor(Color.WHITE);
            resultPanel.setBorder(border);
            resultPanel.setForeground(Color.WHITE);
            resultPanel.setBackground(Color.DARK_GRAY);
            resultPanel.add(intResultLabel, BorderLayout.NORTH);
            resultPanel.add(scrollPane1, BorderLayout.CENTER);
            resultPanel.add(scrollPane2, BorderLayout.SOUTH);
            resultPanel.setBackground(Color.DARK_GRAY);
            
            //Bottom Panel
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(solveButtonPanel, BorderLayout.NORTH);
            bottomPanel.add(resultPanel, BorderLayout.SOUTH);


            // Add panels
            frame.add(controlPanel, BorderLayout.NORTH);
            frame.add(gridPanel, BorderLayout.CENTER);
            frame.add(bottomPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
           
            
            Runnable displayShortest = () ->
            {
                if(shortest.length() != 0)
                {
                    Point currentPoint = new Point(start);
                    for(int i = 0;i < shortest.length()-1;i++)
                    {
                        switch(shortest.charAt(i))
                        {
                            case 'R' : 
                                currentPoint.x++;
                                break;
                            case 'L' : 
                                currentPoint.x--;
                                break;
                            case 'U' : 
                                currentPoint.y--;
                                break;
                            case 'D' : 
                                currentPoint.y++;
                                break;
                        }
                        buttons[currentPoint.y][currentPoint.x].setBackground(new Color(0, 89, 179));
                    }
                }
            };
            //upadating result
            Runnable updateResult = () ->
            {
                //Solving Maze
                Point s = new Point(start);
                Point d = new Point(end);
                java.util.List<Point> travelled = new ArrayList<>();
                travelled.add(s);
                Helper.count = 0;
                java.util.List<String> allWays = Helper.giveWays(maze, "", s, d, travelled);
                
                //Updating Count
                count = Helper.count;
                if(count == 0)
                {
                    JOptionPane.showMessageDialog(frame, "No Solution Found !!!");
                    return;
                }
                intResultLabel.setText("Number of Ways : " + count);
                
                //Updating PathList
                allWays.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
                pathList.setText(String.join("\n", allWays));
                
                //Updating Shortest Path
                shortest = allWays.get(0);
                shortestPath.setText("Shortest Path : "+shortest);
                
                //Displaying Shortest Path : 
                displayShortest.run();
            };
            
            
            
            // Solve button
            solveBtn.addActionListener(e -> {
                if (start.x == -1 || end.x == -1) {
                    JOptionPane.showMessageDialog(frame, "Set both start and end points!");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Solving...");
                updateResult.run();
            });
        });
    }
}
