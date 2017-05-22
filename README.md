# Build a REST-based web service to play Battleship

In the [Milton Bradley game Battleship™](https://en.wikipedia.org/wiki/Battleship_%28game%29), players takes turns picking a coordinate to attack. The goal is to hit all coordinates where your opponent has placed a ship. The first player to do so wins the game.

In this exercise, you will implement a web service that allows a single player to play a simplified version of the game against a board layed out by the computer.

## Requirements

* Your web service must work with the [supplied client](client/client.py). Some sample API responses are also provided in the [sample api response](sample-api-responses.json) file.

* When starting the game, the service will allocate the ships below on a 10 x 10 board.

<table style="margin-left: 4em;">
    <tr><th>#</th><th>Class of ship</th><th>Size</th></tr>
    <tr><td>1</td><td>Carrier</td><td>5</td></tr>
    <tr><td>2</td><td>Battleship</td><td>4</td></tr>
    <tr><td>3</td><td>Cruiser</td><td>3</td></tr>
    <tr><td>4</td><td>Submarine</td><td>3</td></tr>
    <tr><td>5</td><td>Destroyer</td><td>2</td></tr>
</table>

* The service should allow multiple games to be played at the same time. 

* At the end of each `/play` request, the service must return a status of:

    **Miss** - No ship was found at the coordinates.  
    **Hit** - The coordinates hit one of the ships.  
    **Win** - All ships have been sunk.

* Our top priority is to allow a developer to demonstrate that he or she is skilled in his or her craft. If you believe that you can demonstrate this best by completing the puzzle in a different language, then use that language.

## API Routes

* `POST /start-game` - Initiate a game, lay out ship positions, and return a game id.

* `POST /play/{game_id}` - play a turn

* `DELETE /reset-game/{game_id}` - reset the game state

* `GET /status/{game_id}` - get the status of the current game

See the [Swagger spec](battleship-spec.yml) for detailed message description.

## Caveats

* You may elect to use persistent storage for your web service. If you do, please keep the necessary setup for dependencies to a minimum.

* Ships may only be laid out horizontally or vertically, not diagonally.

## Request/Response format

The service request and response formats are defined by the [included Swagger specification](battleship-spec.yml).

Some [sample api response](sample-api-responses.json) are also included.

## Questions and clarifications:

If you have questions about the code puzzle, email [Saar.Picker@staples.com](mailto:saar.picker@staples.com) for clarifications.

## Solution Submission

Fork the project to your github account and make your changes. When you feel you have a working solution, you can submit your code as a pull request against the original repo. Alternatively, you can email your submission as a ZIP file. Then, send an email to [Saar.Picker@staples.com](mailto:saar.picker@staples.com) once you're done.

There are no points given for speed, so please take as much time as you desire,
so that you can submit your best work.

Have fun! :)
