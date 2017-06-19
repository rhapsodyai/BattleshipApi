package org.auclaire.example;

import java.util.Random;
import java.util.UUID;

import org.json.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class BattleshipController {

	boolean carrierSet;
	boolean battleshipSet;
	boolean cruiserSet;
	boolean submarineSet;
	boolean destroyerSet;

	String[][] board;
	String[][] ships;
	String startInput;
	
	UUID gameid = UUID.randomUUID();

	Random r;

	@RequestMapping(value = "start-game", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<String> start(@RequestBody String input) {
		//reinit new game
		reinit(true);
		
		//server response
		JSONObject jsonObj = new JSONObject("{\"game-id\":\"" + gameid + "\"}");

		return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "play/{game-id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<String> move(@RequestBody String input) {
		
		String hitMiss = "";
		int xcoor = 0;
		int ycoor = 0;
		
		//client data
		JSONObject jsonClient = new JSONObject(input);
		xcoor = jsonClient.getJSONArray("coord").getInt(0);
		ycoor = jsonClient.getJSONArray("coord").getInt(1);
		
		if(ships[ycoor][xcoor] == "O") {
			board[ycoor][xcoor] = "hit";
			hitMiss = "hit";
		} else {
			board[ycoor][xcoor] = "miss";
			hitMiss = "miss";
		}	

		//server response
		JSONObject jsonServer = new JSONObject("{\"result\":\"" + hitMiss + "\"}");

		printBoards();
		return new ResponseEntity<String>(jsonServer.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "status/{game-id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> status() {

		//server response
		JSONObject jsonServer = new JSONObject("{\"dimensions\":\"[10,10]\",\"status\":" + jsonBoard(board) + "}");

		jsonBoard(board);
		
		return new ResponseEntity<String>(jsonServer.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "reset-game/{game-id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> reset() {
		
		//reinit without newgame
		reinit(false);

		//server response
		JSONObject jsonServer = new JSONObject("{\"status\":\"success\",\"message\":\"Successfully reset game\"}");

		return new ResponseEntity<String>(jsonServer.toString(), HttpStatus.OK);
	}

	public void placeCarrier(String[][] s) {
		r = new Random();

		int swt = r.nextInt(2);
		int hor = r.nextInt(10);
		int vert = r.nextInt(10);

		//horizontal
		if(swt == 0) {
			if(hor+4 < 10) {
				s[hor][vert] = "O";
				s[hor+1][vert] = "O";
				s[hor+2][vert] = "O";
				s[hor+3][vert] = "O";
				s[hor+4][vert] = "O";
				carrierSet = true;
			}
			//vertical
		} else {
			if(vert+4 < 10) {
				s[hor][vert] = "O";
				s[hor][vert+1] = "O";
				s[hor][vert+2] = "O";
				s[hor][vert+3] = "O";
				s[hor][vert+4] = "O";
				carrierSet = true;
			}
		}
	}

	public void placeBattleship(String[][] s) {
		int swt = r.nextInt(2);
		int hor = r.nextInt(10);
		int vert = r.nextInt(10);

		//horizontal
		if(swt == 0) {
			if(hor+3 < 10) {
				if(s[hor][vert] != "O" && s[hor+1][vert] != "O" && s[hor+2][vert] != "O" && s[hor+3][vert] != "O") {
					s[hor][vert] = "O";
					s[hor+1][vert] = "O";
					s[hor+2][vert] = "O";
					s[hor+3][vert] = "O";
					battleshipSet = true;
				}
			}
			//vertical
		} else {
			if(vert+3 < 10) {
				if(s[hor][vert] != "O" && s[hor][vert+1] != "O" && s[hor][vert+2] != "O" && s[hor][vert+3] != "O") {
					s[hor][vert] = "O";
					s[hor][vert+1] = "O";
					s[hor][vert+2] = "O";
					s[hor][vert+3] = "O";
					battleshipSet = true;
				}
			}
		}
	}

	public void placeCruiserSub(String[][] s, boolean b) {
		int swt = r.nextInt(2);
		int hor = r.nextInt(10);
		int vert = r.nextInt(10);

		//horizontal
		if(swt == 0) {
			if(hor+2 < 10) {
				if(s[hor][vert] != "O" && s[hor+1][vert] != "O" && s[hor+2][vert] != "O") {
					s[hor][vert] = "O";
					s[hor+1][vert] = "O";
					s[hor+2][vert] = "O";
					if(!b)
						cruiserSet = true;
					else
						submarineSet = true;
				}
			}
			//vertical
		} else {
			if(vert+2 < 10) {
				if(s[hor][vert] != "O" && s[hor][vert+1] != "O" && s[hor][vert+2] != "O") {
					s[hor][vert] = "O";
					s[hor][vert+1] = "O";
					s[hor][vert+2] = "O";
					if(!b)
						cruiserSet = true;
					else
						submarineSet = true;
				}
			}
		}
	}

	public void placeDestroyer(String[][] s) {
		int swt = r.nextInt(2);
		int hor = r.nextInt(10);
		int vert = r.nextInt(10);

		//horizontal
		if(swt == 0) {
			if(hor+1 < 10) {
				if(s[hor][vert] != "O" && s[hor+1][vert] != "O") {
					s[hor][vert] = "O";
					s[hor+1][vert] = "O";
					destroyerSet = true;
				}
			}
			//vertical
		} else {
			if(vert+1 < 10) {
				if(s[hor][vert] != "O" && s[hor][vert+1] != "O") {
					s[hor][vert] = "O";
					s[hor][vert+1] = "O";
					destroyerSet = true;
				}
			}
		}
	}

	public static void initBoards(String[][] b, String[][] s) {
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b.length; j++) {
				b[i][j] = "X";
				s[i][j] = "X";
			}
		}
	}

	public static void missileStrike(String[][] b, String[][] s, int coorx, int coory) {
		if(s[coory][coorx] == "O") {
			b[coory][coorx] = "hit";
		} else if(s[coory][coorx] == "X") {
			b[coory][coorx] = "miss";
		}
	}

	public static void printBoard(String[][] b) {
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b.length; j++) {
				if(!(i == (b.length - 1) && j == (b.length - 1)))
					System.out.print(b[i][j] + ", ");
				else
					System.out.println(b[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static JSONArray jsonBoard(String[][] b) {
		JSONArray jArr = new JSONArray();
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b.length; j++) {
				if(!(i == (b.length - 1) && j == (b.length - 1)))
					jArr.put("\"" + b[i][j] + "\"" + ",");
				else
					jArr.put("\"" + b[i][j] + "\"");
			}
		}
		return jArr;
	}
	
	public void printBoards() {
		printBoard(board);
		//printBoard(ships);
	}
	
	public boolean isWin(String[][] b) {
		int counter = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(b[i][j].equals("hit"))
					counter++;
			}
		}
		
		if(counter >= 17) {
			System.out.println("WIN!");
			return true;
		} else {
			return false;
		}
	}
	
	public void reinit(boolean newGame) {
		//reinitialize the game
		this.carrierSet = false;
		this.battleshipSet = false;
		this.cruiserSet = false;
		this.submarineSet = false;
		this.destroyerSet = false;

		board = new String[10][10];
		ships = new String[10][10];

		r = new Random();
		
		if(newGame)
			gameid = UUID.randomUUID();

		initBoards(board, ships);

		//place ships
		while(!carrierSet)
			placeCarrier(ships);
		while(!battleshipSet)
			placeBattleship(ships);
		while(!cruiserSet)
			placeCruiserSub(ships, false);
		while(!submarineSet)
			placeCruiserSub(ships, true);
		while(!destroyerSet)
			placeDestroyer(ships);
		
		//print the current game state
		printBoards();
	}
}


/*
NOTE: Sequences below are provided as examples. Responses should match format and function. 
As some values are not deterministic, an exact match is not expected.

======================================================================

WHEN I send:
 Content-Type: application/json
 POST /start-game

 { "player": "John Doe" }

THEN I should expect:
 Content-Type: application/json
 200 OK

 { "game-id": "52BA9500-5473-429B-9FC8-9438815C195D" }

======================================================================

WHEN I send:
 Content-Type: application/json
 POST /play/52BA9500-5473-429B-9FC8-9438815C195D

 { "coordinate": [1, 0] }

THEN I should expect:
 Content-Type: application/json
 200 OK

 { "result": "hit" }


======================================================================

WHEN I send:
 Content-Type: application/json
 GET /status/52BA9500-5473-429B-9FC8-9438815C195D

THEN I should expect:
 Content-Type: application/json
 200 OK

 { "dimensions": [ 10, 10 ],
   "status": [
        "hit", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "hit", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "hit", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "miss", "X", "X", "X",
        "X", "X", "miss", "X", "X", "X", "X", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "miss", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "X", "X", "X", "X",
        "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"
    ] }

======================================================================

WHEN I send:
 Content-Type: application/json
 GET /reset-game/52BA9500-5473-429B-9FC8-9438815C195D

THEN I should expect:
 Content-Type: application/json
 200 OK

 { "status": "success", "message": "Successfully reset game" }

 */

