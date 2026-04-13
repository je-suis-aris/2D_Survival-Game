<h1 align="center">2D Survival Game</h1>

<p align="center">
  <strong>Grid-based survival game developed in Java with JavaFX</strong><br>
  Real-time movement, resource gathering, crafting, building, combat and save/load support
</p>

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="JavaFX" src="https://img.shields.io/badge/GUI-JavaFX-0A74DA?style=for-the-badge">
  <img alt="Genre" src="https://img.shields.io/badge/Genre-2D%20Survival%20Game-2d2d2d?style=for-the-badge">
  <img alt="Architecture" src="https://img.shields.io/badge/Architecture-OOP-5c4ee5?style=for-the-badge">
</p>

<p align="center">
  <strong>Author:</strong> Aris-Georgian ILIE
</p>

---

## TABLE OF CONTENTS

- [ABOUT THE PROJECT](#about-the-project)
- [GAME CONCEPT](#game-concept)
- [MAIN GAMEPLAY LOOP](#main-gameplay-loop)
- [CORE FEATURES](#core-features)
- [GAME SYSTEMS IN DETAIL](#game-systems-in-detail)
  - [Main Menu and Launch Flow](#main-menu-and-launch-flow)
  - [Grid Map and Player Movement](#grid-map-and-player-movement)
  - [Resources and Gathering](#resources-and-gathering)
  - [Crafting and Building](#crafting-and-building)
  - [Combat System](#combat-system)
  - [Inventory and Item Management](#inventory-and-item-management)
  - [Player Statistics and Progression](#player-statistics-and-progression)
  - [Save and Load System](#save-and-load-system)
  - [Options and Help Interface](#options-and-help-interface)
- [SCREENSHOTS](#screenshots)
- [CONTROLS](#controls)
- [TECHNICAL ARCHITECTURE](#technical-architecture)
- [OBJECT-ORIENTED DESIGN](#object-oriented-design)
- [TECHNOLOGIES USED](#technologies-used)
---

## ABOUT THE PROJECT

<p align="justify">
<strong>2D Survival Game</strong> is an interactive survival project developed in <strong>Java with JavaFX</strong>. The game takes place on a grid-based map where the player moves using the keyboard, gathers resources, fights enemies, crafts equipment, builds useful structures and manages survival over time. The project combines game logic from object-oriented programming with a graphical user interface that updates in real time as the player interacts with the world.
</p>

<p align="justify">
The game was designed as a full playable application rather than a small technical demo. It includes a launch menu, a game map, combat windows, building and inventory interfaces, player statistics, help and options panels and a save/load system that allows the player to continue from a previously saved state.
</p>

<p align="justify">
Every new session can generate a fresh randomized map, which means the distribution of resources and enemies changes from game to game. As the player moves across the matrix, the interface reacts immediately. If a resource is collected, that tile becomes empty. If a structure is built, that tile becomes occupied. If an enemy is encountered, a dedicated combat window opens and the result of the fight affects the main game state.
</p>

<p align="justify">
The project also puts strong focus on clean object-oriented structure. It uses abstract classes, inheritance, enums, interfaces, exceptions, file operations and inventory sorting through comparison logic. Because of that, the game works both as an interactive survival experience and as a solid academic software project built around OOP principles.
</p>

---

## GAME CONCEPT

<p align="justify">
The idea behind the game is to place the player in a hostile environment where survival depends on movement, resource management and decision making. The world is represented as a matrix of cells. Some cells are empty, some contain resources and some contain enemies or special objects. The player moves one cell at a time and must decide how to use the available space and materials.
</p>

<p align="justify">
The player collects the main resources needed for survival and progression: <strong>wood</strong>, <strong>stone</strong> and <strong>food</strong>. These resources are important because they support multiple systems in the game. They allow the player to build structures, craft useful items and maintain progress while exploring the map.
</p>

<p align="justify">
The game is not only about collecting. It also introduces danger through enemies placed on the map. When the player reaches an enemy tile, combat begins in a separate battle interface. Winning these fights can reward the player with points, dropped items and additional resources. Losing can reduce health or end progress, depending on the situation.
</p>

<p align="justify">
Because movement, gathering, combat, crafting and building are all connected, the game creates a complete loop where each action matters. The player is constantly choosing whether to explore, fight, invest in structures or preserve resources for later use.
</p>

---

## MAIN GAMEPLAY LOOP

<p align="justify">
The gameplay loop is structured around exploration and survival on a dynamic 2D map.
</p>

<p align="justify">
At the beginning of a new game, the player enters a randomly generated map. The grid contains a mixture of empty cells, resource nodes and enemies. Using the <strong>W</strong>, <strong>A</strong>, <strong>S</strong> and <strong>D</strong> keys, the player moves through the map one tile at a time and interacts with what is found in the environment.
</p>

<p align="justify">
When the player reaches a resource tile, the resource can be collected and added to the player's totals. The tile then changes visually to an empty cell, which gives immediate feedback that the interaction was successful.
</p>

<p align="justify">
When the player reaches an empty tile, that space can be used strategically. The player may decide to build a structure there or keep moving. Structures provide long-term utility and can improve survivability through healing, defense or other gameplay effects.
</p>

<p align="justify">
When the player reaches an enemy, the game opens a separate combat scene or window. The player and the enemy exchange attacks until one of them is defeated. The outcome affects health, score, dropped rewards and the player's future decisions.
</p>

<p align="justify">
This loop continues throughout the session.
</p>

1. explore the map  
2. collect resources  
3. manage health and items  
4. fight enemies when necessary  
5. build structures on empty cells  
6. improve long-term survival through planning and resource use  

<p align="justify">
Because all of these systems update the interface immediately, the player always sees the direct consequences of movement and actions.
</p>

---

## CORE FEATURES

### Real-time grid-based movement
<p align="justify">
The player moves across a matrix-based world using keyboard input. Each movement changes position on the grid and updates the interface in real time.
</p>

### Randomized map generation
<p align="justify">
Each new game creates a different map layout with randomized placement of resources and enemies, which makes repeated sessions less predictable.
</p>

### Resource gathering
<p align="justify">
The player can collect wood, stone and food from dedicated map tiles. Gathered resources are immediately reflected in the interface.
</p>

### Crafting and building
<p align="justify">
Empty cells can be used to create new objects or construct structures that provide useful gameplay effects and strategic benefits.
</p>

### Separate combat interface
<p align="justify">
When the player encounters an enemy, a dedicated combat window opens and displays the battle flow, health values and reward outcomes.
</p>

### Inventory management
<p align="justify">
Collected and dropped items are stored in an inventory system that can be displayed and sorted according to selected criteria such as value.
</p>

### Player statistics
<p align="justify">
The game tracks important player data including health, attack, defense, experience, carrying capacity and current carried weight.
</p>

### Save and load support
<p align="justify">
The current game state can be saved to files and loaded later so the player can continue from the same position with the same resources and inventory.
</p>

### Full GUI flow
<p align="justify">
The project includes a main menu with Resume, New Game, Options, Help and Exit, as well as multiple secondary windows for gameplay features.
</p>

---

## GAME SYSTEMS IN DETAIL

### Main Menu and Launch Flow

<p align="justify">
The game begins with a full menu interface that acts as the entry point to the application. From this screen, the player can start a new randomized game, resume a previous session, open the options menu, read the help instructions or exit the application.
</p>

<p align="justify">
This launch structure is important because it makes the project feel complete and user-oriented. Instead of opening directly into gameplay, the application behaves like a full game with a proper starting flow and multiple player-facing options.
</p>

<p align="justify">
The resume feature is especially useful because it connects the menu to the save/load system. This makes the game practical to revisit and also demonstrates file reading functionality in a meaningful gameplay context.
</p>

---

### Grid Map and Player Movement

<p align="justify">
The main game world is represented as a square matrix where each tile contains one type of content: empty space, a resource, an enemy or a constructed structure.
</p>

<p align="justify">
The player moves using the standard <strong>W</strong>, <strong>A</strong>, <strong>S</strong> and <strong>D</strong> keys. Each move changes the current grid position by one cell. This movement system is simple to understand, but it is central to the whole project because almost every gameplay mechanic depends on which tile the player is currently occupying.
</p>

<p align="justify">
The interface reacts immediately when the player moves. If the new tile contains a resource, the collection effect becomes visible. If it contains an enemy, the combat sequence starts. If it is empty, the player may use it for building or simply continue exploring.
</p>

<p align="justify">
This direct relationship between tile position and game state makes the map system very clear and effective for a survival game built around interaction and progression.
</p>

---

### Resources and Gathering

<p align="justify">
The main collectible resources are <strong>wood</strong>, <strong>stone</strong> and <strong>food</strong>. They are represented on the map through different visual elements, which makes it possible for the player to identify them directly from the interface.
</p>

<p align="justify">
When the player reaches one of these tiles, the resource is collected and added to the player's total. The interface updates instantly, and the tile becomes empty to show that the object has been consumed or removed from the world.
</p>

<p align="justify">
This system is important because it gives the player a reason to explore and a practical reward for movement. Resources are not decorative. They are used later for crafting and construction, which means gathering has lasting impact on gameplay.
</p>

<p align="justify">
The game also treats resource collection as part of strategy. Choosing whether to gather immediately, continue exploring or prepare for combat creates small but meaningful decisions throughout the session.
</p>

---

### Crafting and Building

<p align="justify">
One of the more interesting parts of the project is the ability to use empty map cells for construction. If the player stands on or reaches an empty tile, that space can become the location for a crafted structure.
</p>

<p align="justify">
Buildings are more than static decorations. They have gameplay effects and can change how the player survives. For example, a healing structure can restore health, while other buildings may improve defensive or combat-related statistics. This makes construction feel useful rather than cosmetic.
</p>

<p align="justify">
Building also introduces resource planning. The player must decide how much wood and stone to spend and whether the chosen location is worth occupying permanently. Since placed buildings take up space on the matrix, each construction decision changes the future shape of the map.
</p>

<p align="justify">
The building system therefore adds long-term planning to the game and connects resource gathering to progression in a natural way.
</p>

---

### Combat System

<p align="justify">
Combat happens when the player moves onto a tile occupied by an enemy. Instead of resolving the fight invisibly, the game opens a dedicated combat window that clearly presents both participants, their health values and the battle log.
</p>

<p align="justify">
The player attacks first, after which the enemy responds. This exchange continues until one side is defeated. Because the battle is displayed in its own window, the player can follow the action step by step and understand exactly how the fight develops.
</p>

<p align="justify">
The result of combat affects several systems at once. Health can decrease, points can increase and the player may obtain dropped items or additional resources after a victory. This makes combat an important source of both danger and reward.
</p>

<p align="justify">
The reward window shown after battle helps reinforce progression. It gives a clear summary of what was gained from the encounter and makes each victory feel meaningful.
</p>

---

### Inventory and Item Management

<p align="justify">
The inventory stores items gathered during exploration or dropped by defeated enemies. These items can improve the player through bonuses such as better attack, defense or other stat-related advantages.
</p>

<p align="justify">
The system is more than a simple storage list. Items can be displayed through a dedicated inventory interface, and sorting logic can be applied to organize them based on selected criteria such as monetary value. This reflects the use of comparison-based programming techniques inside the project and gives the inventory a stronger technical foundation.
</p>

<p align="justify">
Inventory management matters because it supports both the survival and progression sides of the game. Valuable items improve the player, while clear display and sorting help keep the state understandable and structured.
</p>

---

### Player Statistics and Progression

<p align="justify">
The game tracks a set of player statistics that influence survival and combat. These include health, attack, defense, level, experience, carrying capacity and current weight.
</p>

<p align="justify">
Displaying this information in a separate statistics interface helps the player understand their current condition and progress. It also makes the game feel more complete because the player is not only moving on a map, but developing a character with measurable strengths and limitations.
</p>

<p align="justify">
Carrying capacity is especially useful in a survival game because it adds a practical constraint to inventory use. It encourages planning and prevents item collection from becoming meaningless accumulation.
</p>

---

### Save and Load System

<p align="justify">
The game includes persistent save and load functionality through file input and output. This allows the current session to be stored and reopened later with the same map state, player position, resource totals and inventory contents.
</p>

<p align="justify">
This feature is important both technically and practically. From a software perspective, it demonstrates meaningful file handling. From a gameplay perspective, it makes the project easier to use, because players do not need to restart every time they close the application.
</p>

<p align="justify">
The Resume option in the main menu is directly connected to this system, which helps integrate persistence naturally into the overall user experience.
</p>

---

### Options and Help Interface

<p align="justify">
The project includes secondary interfaces that improve usability and presentation. The <strong>Options</strong> window allows changes such as background selection and music preferences, which helps personalize the experience.
</p>

<p align="justify">
The <strong>Help</strong> window explains the controls, story context and game objectives. This is especially useful for first-time users because it gives them immediate guidance without needing external documentation.
</p>

<p align="justify">
Together, these panels make the project more polished and accessible. They show that the game is designed not only to function technically, but also to communicate clearly with the player.
</p>

---

## SCREENSHOTS

### Main Menu

<p align="center">
  <img src="2D_game_source_code/homepage.PNG" alt="2D survival game main menu" width="900">
</p>

<p align="justify">
The main menu is the entry point of the application. It provides access to the core actions of the game: starting a new session, resuming a saved one, opening the options panel, reading the help section and closing the application.
</p>

### Help Window

<p align="center">
  <img src="2D_game_source_code/help.PNG" alt="2D survival game help window" width="500">
</p>

<p align="justify">
The help interface explains the controls, the game context and the player's objectives. It makes the project easier to understand for someone who launches it for the first time.
</p>

### Options Window

<p align="center">
  <img src="2D_game_source_code/options.PNG" alt="2D survival game options window" width="900">
</p>

<p align="justify">
The options panel allows basic customization of the experience, including visual background selection and music-related settings.
</p>

### Main Map View

<p align="center">
  <img src="2D_game_source_code/construction_2.PNG" alt="2D survival game map view" width="900">
</p>

<p align="justify">
This screenshot shows the main gameplay grid, the real-time resource display and the side action buttons. It illustrates how the player experiences the map during normal exploration.
</p>

### Building Interface

<p align="center">
  <img src="2D_game_source_code/construction_1.PNG" alt="2D survival game construction interface" width="900">
</p>

<p align="justify">
The construction window allows the player to choose which structure to build on an empty tile and displays the associated costs and effects.
</p>

### Combat Window

<p align="center">
  <img src="2D_game_source_code/battle.PNG" alt="2D survival game combat window" width="900">
</p>

<p align="justify">
Combat takes place in a separate interface where the player and enemy face each other directly. Health bars and the battle log help make the fight easy to follow.
</p>

### Combat Rewards

<p align="center">
  <img src="2D_game_source_code/battle_2.PNG" alt="2D survival game combat rewards" width="900">
</p>

<p align="justify">
After victory, the game shows a reward summary including dropped items, collected resources and earned points. This makes combat outcomes clear and satisfying.
</p>

### Inventory Window

<p align="center">
  <img src="2D_game_source_code/inventory.PNG" alt="2D survival game inventory window" width="900">
</p>

<p align="justify">
The inventory interface displays the player's collected items and shows how they can be organized through sorting logic.
</p>

### Statistics and Inventory on Map

<p align="center">
  <img src="2D_game_source_code/map_inventory.PNG" alt="2D survival game stats and inventory" width="900">
</p>

<p align="justify">
This screenshot shows the in-game map together with secondary management windows such as player statistics and inventory-related information. It illustrates how gameplay data remains visible and accessible while playing.
</p>

---

## CONTROLS

| Action | Input |
|---|---|
| Move up | `W` or `↑` |
| Move down | `S` or `↓` |
| Move left | `A` or `←` |
| Move right | `D` or `→` |
| Interact with map cell | Movement onto the target tile |
| Open menu-related features | Use on-screen buttons |

---

## TECHNICAL ARCHITECTURE

<p align="justify">
The project is built as a modular Java application where gameplay logic and interface logic work together.
</p>

<p align="justify">
The <strong>game state layer</strong> stores the player, map, resources, enemies, structures, score and progression data.
</p>

<p align="justify">
The <strong>map layer</strong> represents the world as a matrix and handles the content of each tile, including resources, buildings, enemies and empty cells.
</p>

<p align="justify">
The <strong>character layer</strong> manages the player and enemy entities, their stats and the rules for taking and dealing damage.
</p>

<p align="justify">
The <strong>inventory and item layer</strong> handles item ownership, sorting, bonuses and item display logic.
</p>

<p align="justify">
The <strong>GUI layer</strong> presents the menu, map, combat windows, help, options, construction and inventory interfaces using JavaFX.
</p>

<p align="justify">
The <strong>persistence layer</strong> manages save and load operations through file reading and writing.
</p>

<p align="justify">
This separation makes the project easier to maintain and helps demonstrate clean OOP design rather than mixing all responsibilities inside a single class.
</p>

---

## OBJECT-ORIENTED DESIGN

<p align="justify">
A major strength of the project is its use of object-oriented programming principles.
</p>

<p align="justify">
The game logic is built around <strong>abstract classes</strong> that generalize important concepts such as characters and gatherable objects. This allows shared behavior to be defined once and specialized in subclasses such as player, enemy or specific resource types.
</p>

<p align="justify">
<strong>Inheritance</strong> is used to structure related gameplay entities. This makes the code more organized and avoids unnecessary duplication.
</p>

<p align="justify">
<strong>Enums</strong> are used for controlled values such as quality or category, which helps keep the system safer and easier to understand.
</p>

<p align="justify">
<strong>Interfaces</strong> are used where behavior should be defined independently from a single class hierarchy.
</p>

<p align="justify">
<strong>Exceptions</strong> help handle invalid actions and edge cases cleanly, preventing the application from crashing when commands are not valid.
</p>

<p align="justify">
The inventory also demonstrates the use of <strong>Comparable</strong> or <strong>Comparator</strong> logic for sorting items according to meaningful properties such as value.
</p>

<p align="justify">
Together, these choices make the project academically solid and technically well structured.
</p>

---

## TECHNOLOGIES USED

| Technology | Role in the project |
|---|---|
| **Java** | Main programming language |
| **JavaFX** | GUI framework for scenes, windows and controls |
| **Object-Oriented Programming** | Core design approach for classes and gameplay logic |
| **File I/O** | Save and load game state |
| **Abstract Classes** | Generalization of shared game entities |
| **Inheritance** | Extension of base classes into specialized gameplay objects |
| **Enums** | Controlled categorical values |
| **Interfaces** | Reusable behavior contracts |
| **Exceptions** | Safe handling of invalid or extreme cases |
| **Comparable / Comparator** | Inventory sorting and ordering logic |
| **CSS / JavaFX Styling** | Visual customization of the interface |

---
