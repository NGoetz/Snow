# Snow
Arctic predator-prey simulation

Simulates a biotop in the Arctic, containing prey (rabbit) and predator (fox). It contains a simulation based on the properties of the animals set in their classes. The world is a two-dimensional int-Array. Every animal is simulated for its own.
By using the method initialize(), the program tires to place the requested amount of animals. Every tick, foxes can hunt
(find and replace rabbits in their surroundings) or move, rabbits can move (they can't see foxes, so movement is random), new animals can be born if there is space (animals can only reproduce after serveral ticks. If mulitiple trys to place a child failed, it will not be born
(simulation of stress). Foxes have to eat rabbits to survive, rabbits have a chance to find food every turn.
You can enable the mutation of the genome of every animal and simulate evolution by this, but be aware that this can quickly lead to instable systems.
The GUI contains a complete control panel, with infos, a pause button and the possiblity to start the game with a fixed amount of animals.
There are also two visualizations: A two dimensional world map with every animals drawn in and a long term graphic showing the amount
of animals of each species.

Note: The best results are achieved by a starting amount of 2000 foxes and 4000 rabbits without mutations. As this is a Monte-Carlo-simulation, results are stochastic.

Disclaimer: This is a toy program. Treat it as a demonstration of a simple predator-prey simulation. 
