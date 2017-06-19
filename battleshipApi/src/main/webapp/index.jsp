<html>
<head>
<title>Welcome to Battleship Server!</title>

<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
</head>
<body>
<style type="text/css">
body {
  font-family: 'Roboto', sans-serif;
  font-size: 10;
}

h2 {
  background: rgba(0,0,0,0.08);
}

h4 {
  background: rgba(0,0,0,0.08);
}
</style>

<h2 style="background: rgba(0,0,0,0.08)">Welcome to Battleship Server!</h2>

<h4 style="background: rgba(0,0,0,0.08)">How to test the server:</h2>
Send the following command to test the server. This command returns a JSON formatted String as a response token.

<pre>curl -H "Content-Type: application/json" -X POST -d '{ "player": "John Doe" }' http://localhost:8082/battleshipApi/start-game
</pre>

<h4 style="background: rgba(0,0,0,0.08)">How to play:</h2>

There are 4 basic operations:

<ul>
<li>/start-game</li>
<li>/play/{game-uuid}</li>
<li>/status/{game-uuid}</li>
<li>/reset-game/{game-uuid}</li>
</ul>
</body>
</html>

<h4>POST /start-game</h4>
<pre>python client.py --start -p John\ Doe</pre>
<p>This operation begins the game, intializes the ship placements and returns a game uuid consisting referring to a game state in the new game state.</p>

<h4>POST /play/{game-uuid}</h4>
<pre>python2 client.py --play -g 7dcb55b6-92a0-4b11-8247-cce8a3fcab95 -c 1,5</pre>
<p>This operation executes a single move (missile shot). Depending on the location of the ships, this can return a hit or miss.
(If the game is won, then the player will be informed of the good news.)</p>

<h4>GET /status/{game-uuid}</h4>
<pre>python2 client.py -g 82ad763e-5f84-40a2-ae34-667f1f0ca0a3 -s</pre>
<p>This operation requests a copy of the current gameboard state consisting of current hits and misses as well as unmarked squares. The game board describes previous moves.</p>

<h4>GET /reset-game/{game-uuid}</h4>
<pre>python2 client.py -g 756cc296-b78a-4f81-8329-0b93c7da0d1b --reset</pre>
<p>This operation resets the game state of the current game.</p>

<h4>Contact info:</h4>
<p>Please email me at rhapsody.ai [ at ] gmail if you have any questions or comments.</p>
