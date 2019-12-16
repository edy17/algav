# TME: heuristiques pour le problème de voyageur de commerce multiple, avec budget.

#score
- 428 : 10/20
- 443 : 15/20
- 454 : 18/20

#Instruction
- Cinq voyageurs de commerce alice, bob, cindy, david et eddy disposant chacun d'un budget de 1664 dollars; 
- calculer leur itinéraire dans le but de visiter le plus grand nombre de villes possibles, en partant chacun de la ville "maison", et en revenant chacun à la "maison" à la fin de leur parcours;
- Chacun possède 1664 dollars;
- Ne pas faire de fail : ne pas depenser plus d'argent que la personne n'en possède.``````

#strategie possible : 
- limiter le nombre de maisons dans lequels chacun va passer. genre 100 ou 90 maximum et 1664 dollars. par personnes au maximum. 
- But calculer 5 listes qui passent par le plus de points possible avec un budget limité.
#Idée : 
- Pizza : on coupe la map en plusieurs morceaux et chacun ne peut aller que a un seul endroit.
- Pour alice par exemple on elève toutes les zones sauf celle d'alice et on applique l'algorithme TME_TSP.
- Pour couper on peut utiliser 2pi/5 pour delimiter les zones.
- Ensuite on applique l'algorithme: Tant que c'ets trop chère on enlève les points de la cartes et on refait le calcul.

#Autre idée : faire une vrai pizza, ronde. Toujours avec 5 zones.En fonction de la distance. On filtre .
#Autre idée : tour à tour. Concurrence équitable.
