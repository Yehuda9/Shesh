# Shesh
server always save map, where game id is key and game object is value.
server exposes several methots you can use for building your sheshbesh client:

Init: 

To sign for game.
return user id which is game id with 0/1 at end char for color.
function assign user for existing game or creating new game if needed.

Triangle:

Recieve triangle index and game id.
Return Json representing the triangle object in the game.

Pour:

recieve game id.
return 4 numbers from 1 to 6, representing the result of rolling 2 dices.
Most of the time, 2 of the 4 numbers will be 0, only in case of 'double', the result will be (x,x,x,x).

CurrentPlayer:

recieve game id and return the current player turn in the game.

IsValidMove:

recieve 2 integers from 0 to 23 for source and destenation and game id.
return true if move is valid.

MakeMove:

recieve 2 integers from 0 to 23 for source and destenation and game id.
If move is valid, this method will make the move.

AvailableMoves:
recieve game id.
return list of all the valid moves.
