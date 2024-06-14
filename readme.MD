# Scratch Game

Scratch Game is a Java application that simulates a scratch card game based on provided configurations. Players can set their betting amount and the game generates a matrix of symbols. The game checks for winning combinations within the matrix and calculates rewards accordingly, including applying bonus symbols that modify the reward.

## Features

- Generates a matrix of symbols based on provided configurations.
- Checks for winning combinations both for same symbols and linear patterns.
- Calculates rewards based on winning combinations and betting amount.
- Applies bonus symbols that can modify the reward.
- Outputs the game result in JSON format.

## Setup

### Prerequisites

To run this application, you need:

- Java Development Kit (JDK) 8 or higher
- Maven (for building the project)

### Installation

1. Clone the repository:

   ```bash
   extract archive provided
   cd scratch-game

2. Build the project using Maven:
    ```bash
    mvn clean install
   
3. Usage
Once the project is compiled and packaged into a JAR file, you can run it using the java -jar command:
To run the Scratch Game, use the following command:
```bash
java -jar scratch-game.jar --config <config-file> --betting-amount <betting-amount>

Replace <config-file> with the path to your JSON configuration file and <betting-amount> with the desired betting amount.

5.

### Notes:
