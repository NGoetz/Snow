# Snow
Arctic predator-prey simulation
Simulates a biotop in the Arctic containing prey (rabbit) and predator (fox). Arctic contains the simulation based on the properties of the animals
set in their classes. The world is a two-dimensional int-Array. Every animal is simulated for its own.
By using the method initialize(), it is tryed to place the requestet amount of animals. Every tick, foxes can hunt
(try to eat rabbits in their neighborhood) or move, rabbits can move (they can't see foxes), new animals can be born and try to place 
themself in the gameworld (animals can only reproduce after serveral ticks. If mulitiple trys to place a child failed, it will not be born
- simulation of stress). Foxes have to eat rabbits to survive, rabbits have a chance to find food every turn.
The GUI contains a complete control panel, with infos, a pause button and the possiblity to start the game with an fix amount of animals.
There are also to visualizations: A two dimensional world map with every animals drawn in and a long term graphic showing the amount
of animals of each spezies.
