# Jungle Chess (Dou Shou Qi)

A desktop implementation of **Jungle Chess / Dou Shou Qi**, built with Java Swing. The project covers the core CS109 requirements - board initialization, rule validation, saving/loading, and a graphical user interface - and adds single-player modes, undo/replay, themes, piece styles, background music, and interaction feedback.

> This README is based on the current `main` branch, the repository's 81 Git commits, and the *23Spring CS109 Project Specifications - Jungle Chess* document. Feature descriptions reflect the current implementation.

## Features

- **Local two-player mode**: Blue moves first; players alternate between moving and capturing. A player wins by entering the opponent's den or capturing all opponent pieces.
- **Complete board and pieces**: A 7 x 9 board with dens, traps, and two rivers. Each side has an elephant, lion, tiger, leopard, wolf, dog, cat, and rat.
- **Rule validation and move hints**: Selecting one of your pieces highlights its legal moves and captures while preventing invalid moves involving dens, rivers, and other terrain rules.
- **Special rules**: Rat movement in rivers; capture restrictions between land and river rats; rat-versus-elephant rules; lion/tiger river jumps blocked by rats; and trap-related capture rules.
- **Single-player mode**: Three AI difficulty levels, with the AI playing Red. The strategies are random moves, moves that favor advancing toward the opponent's den, and prioritizing the highest-value capture.
- **Game management**: Restart, undo, animated move replay, and save/load support. Loading replays recorded moves and resets the game after detecting invalid or unreadable data.
- **Interface customization**: Main and in-game menus, a rules dialog, four seasonal themes, two piece styles, hover/click feedback, background music, and button sound effects.

## Rules at a Glance

Piece ranks, from high to low, are: Elephant (8), Lion (7), Tiger (6), Leopard (5), Wolf (4), Dog (3), Cat (2), and Rat (1). A piece can normally capture an equal- or lower-ranked enemy piece, with one exception: a rat can capture an elephant, but an elephant cannot capture a rat.

- Pieces normally move one square horizontally or vertically; diagonal moves are not allowed.
- Only rats may enter a river. A rat in a river cannot be captured by a land piece and cannot capture an elephant on land.
- Lions and tigers may jump over an entire river horizontally or vertically. Any rat in the river blocks the jump.
- A piece cannot enter its own den. Entering the opponent's den wins the game.
- An enemy piece in one of your traps can be captured by any of your pieces.

## Requirements and Running the Project

- JDK 17 or later is recommended; the source uses modern `switch ->` syntax.
- No external libraries are required. Resources are loaded from relative paths, so run the application from the repository root.

### IntelliJ IDEA

1. Open the project root in IntelliJ IDEA.
2. Set the project SDK to JDK 17 or later.
3. Mark `src` as a Sources Root if IntelliJ does not do so automatically.
4. Run the `Main` class in `src/Main.java`.

### Command Line

From the project root, run:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName)
java -cp out Main
```

In the main menu:

- Choose `Single Player` and then a difficulty level to play Blue against the Red AI.
- Choose `Multi Player` for local two-player play.
- Choose `Settings` to view the rules. Theme and piece-style settings are available from the in-game settings menu.

## Saving, Loading, and Replay

The in-game menu provides `Save` and `Load` through a file chooser. A save stores a Java-serialized list of moves, including each move's turn number, source, destination, moving piece, and captured piece. Use a `.txt` filename when saving. On load, the game starts from the initial board and replays every recorded move; it displays a warning and resets the board if a move is invalid or the file cannot be read.

- `Undo` removes one move in local two-player mode, or the player's and AI's moves in single-player mode.
- `Replay` plays all recorded moves at roughly 0.5-second intervals.
- `Restart` restores the initial board, turn state, and move history.

## Windows Executable Packaging

An executable named `Jungle.exe` was produced with **exe4j** during the original project work. It is not tracked in this repository because the current source tree does not include the exe4j project configuration or a reproducible packaging script.

The application loads images and audio through relative filesystem paths such as `resource/...`. Consequently, `Jungle.exe` should not be treated as a standalone portable file: copying only the executable to another directory can prevent it from locating its assets or the runtime files expected by its original exe4j package. To distribute an executable reliably, package it from the source with exe4j and ship the complete generated distribution, including the required `resource/` directory and any runtime files selected in the exe4j configuration.

### Current Portable Build

A new self-contained Windows application image is generated from the current source with `jpackage` and is available locally as `dist/Jungle-Windows.zip`. Extract the archive and run `Jungle/Jungle.exe`. The package contains the launcher, a bundled Java runtime, the application JAR, and its `resource/` directory. `PackagingBootstrap` sets the packaged application's working directory before starting the game so that relative resource paths work after extraction.

The `dist/` directory is intentionally ignored by Git. Upload the ZIP as a GitHub Release asset instead of committing it to the repository history.

## Project Structure

```text
.
├── src/
│   ├── Main.java                         # Application entry point and background music startup
│   ├── model/                            # Board, pieces, coordinates, moves, and player colors
│   ├── controller/GameController.java    # Game flow, AI, save/load, and win detection
│   ├── view/                             # Swing frames, board, cells, and piece components
│   │   ├── AnimalChessComponent/         # Visual components for the eight animals
│   │   └── UI/                           # Rounded buttons, borders, and image panel
│   ├── listener/                         # View event interface
│   └── music/                            # Audio playback thread
├── resource/
│   ├── AnimalIcons/                      # Two sets of piece icons
│   ├── Backgrounds/                      # Menu and seasonal-theme backgrounds
│   ├── CellIcons/                        # Terrain and interaction-state images
│   └── Music/                            # Background music and click sound
├── ChessDemo.iml / Project.iml           # IntelliJ IDEA project configuration
└── InvalidMove.txt                       # Example/test file for invalid saved moves
```

The code follows a lightweight MVC structure: `model` owns board state and rule checks, `view` renders Swing components and visual feedback, and `GameController` coordinates interaction, state updates, AI behavior, and saved games.

## Mapping to the Course Specification

| Specification task | Current implementation |
| --- | --- |
| Task 1: Initialization and restart | Initializes the 7 x 9 board, 16 pieces, special terrain, turn count, and active player; includes a restart button. |
| Task 2: Saving and loading | Saves and restores the game through move history; validates and replays moves on load, warning and resetting after invalid data. |
| Task 3: Gameplay | Supports alternating turns, captures, den/all-piece win detection, and rules for rats, lions, tigers, rivers, and traps. |
| Task 4: GUI | Uses Java Swing for menus, board rendering, status displays, dialogs, and file selection. |
| Task 5: Advanced features | Includes a start menu and game-mode selection, three AI levels, legal-move hints, undo/replay, themes, piece styles, sound effects, and background music. |

## Git Development History

The repository history spans **2023-05-15** to **2023-05-29** and contains **81** commits on the current `main` branch. The commits show an incremental path from a basic playable board to rule completeness, UI polish, and advanced features:

1. **Core model and game flow**: Created the board, pieces, click controller, and win detection; then refined elephant/rat behavior, river entry, lion/tiger jumps, traps, and own-den restrictions.
2. **Board visuals and interaction**: Added distinct animal icons, terrain artwork, hover states, rounded gradient buttons, and the main menu.
3. **Recoverable games**: Added the `Steps` move model, followed by saving, load-time validation, undo, and replay.
4. **Modes and presentation**: Added difficulty-selectable AI, settings, a rules dialog, seasonal themes, piece styles, click sounds, and background music.
5. **Final iteration**: The final commit is `245bc34` ("final version"); the preceding commits include piece-style customization and UI refinements.

Based on Git author signatures, the main contributors are `hehehelahehe` (52 commits) and `12210823` (53 commits across two email identities).

## Known Limitations

- Saved games are Java-serialized binary data. Although the UI suggests a `.txt` extension, the files are not editable plain text.
- The project has no Maven/Gradle build configuration or automated tests. IntelliJ IDEA or the `javac` command above is recommended.
- The AI uses single-move rule-based heuristics rather than search-based game planning.
- The original exe4j-built `Jungle.exe` is not a reproducible or independently portable release in the current repository; use the source build instructions unless a complete exe4j distribution is recreated.

## References

- Course specification: *23Spring CS109 Project Specifications - Jungle Chess*.
- Remote repository: <https://github.com/12210823/Project>
