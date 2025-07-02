# Maze Solver 🧭

A highly interactive **Java Swing GUI application** that allows users to create custom mazes, define start/end points, and **solve them using the backtracking algorithm**. It visualizes **all possible paths** from start to end and highlights the **shortest path**.

## 🧠 Features
- 50×50 grid maze builder
- Toggle between modes:
  - 🧱 **Obstacle**
  - 🟢 **Start Point**
  - 🔴 **End Point**
- Real-time pathfinding using **Backtracking**
- Displays:
  - Total number of valid paths
  - All possible paths as direction strings (`R`, `L`, `U`, `D`)
  - Shortest path visually highlighted
- Clear and Fill options to reset or block the maze

## 🛠️ Tech Stack
- Java
- Java Swing (GUI)
- Custom Backtracking Algorithm
- AWT & Swing for UI

## 🚀 How to Run

1. Clone or download the repository.
2. Open it in any Java IDE (e.g., NetBeans, IntelliJ).
3. Run `MazeSolver.java`.

## ⚠️ Notes
- Start and End points **must be set** before solving.
- Grid cells use:
  - ⬜ White: Open path
  - ⬛ Black: Obstacle
  - 🟩 Green: Start
  - 🟥 Red: End
  - 🔵 Blue: Shortest path traced
