This is the server side of MED3 PCSS miniproject of group 301.

The client side can be found here: https://github.com/MikkMunk/P3_programming-client

Other information regarding the game and the server can be found in the Wiki of the repository, including:
  - Rules of the original "Codenames" game
  - How to play the game
  - Word generation principles and source
  - UML Diagrams for the server side (Class, Sequence, Use case)

The rules and instructions on how to play the game can also be found on the client side's wiki.



Instructions how to download and run the game:

- It is useful to read the general game rules in the wiki to familiarize with the "Codenames" game concept.
- One person has to download and host the server, whereas 4 people are required to use the clients. One of the players can both host the server, and the client, if necessary.
- The players involved (both server and clients) must be connected to the same network as the server.
- The clients must acquire the the IP address of the host computer and insert it on the Client side (when creating the Socket in line 29 of the "Main" class).
- The server must be run first, and then clients join one by one. They are assigned teams accordingly (as seen in "How to play the game" in the Wiki).

Note; the game has to be run in IntelliJ for the UI to function properly.
